package dev.itssho.module.qpay.module.structure.ui.delegate

import com.esotericsoftware.tablelayout.swing.Table
import contentEquals
import coroutine.observe
import dev.itssho.module.component.VerticalPanel
import dev.itssho.module.component.components.button.JIPlainButton
import dev.itssho.module.component.util.addOnCheckListener
import dev.itssho.module.component.util.addOnClickListener
import dev.itssho.module.component.util.addOnSelectListener
import dev.itssho.module.core.ui.UserInterface
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.HierarchyObject.*
import dev.itssho.module.hierarchy.attr.BackText
import dev.itssho.module.hierarchy.attr.FrontText
import dev.itssho.module.hierarchy.text.Text
import dev.itssho.module.hierarchy.extension.attributeOrNull
import dev.itssho.module.hierarchy.extension.flatten
import dev.itssho.module.hierarchy.extension.hasParent
import dev.itssho.module.qpay.module.structure.ui.entity.Pad
import dev.itssho.module.ui.util.constructor.*
import kotlinx.coroutines.CoroutineScope
import swing.setItems
import swing.setSelectedIfDiffer
import swing.setSelectedIndexIfDiffer
import swing.setTextIfDiffers
import javax.swing.JCheckBox
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

// TODO Вынести пакет из structure.ui
class TreePanelUi(private val viewModel: TreePanelViewModel, scope: CoroutineScope) : UserInterface(scope) {

	private val panel: JPanel = VerticalPanel()
	private val componentRegistry = LinkedHashMap<String, TreeItem>()

	override fun constructView(): JComponent {
		viewModel.loadStructure()
		return panel
	}

	fun initViewModelObserve() {
		viewModel.state.observe(scope) { state ->
			when (state) {
				is TreePanelState.Loading -> setLoadingState()
				is TreePanelState.Content -> setContentState(state.structure)
			}
		}
	}

	private fun setLoadingState() {
		panel.removeAll()
		componentRegistry.clear()
	}

	private fun setContentState(structure: HierarchyObject) {
		val flatStructure = structure.flatten()

		val newIds = flatStructure.map { it.id }
		val previousIds = componentRegistry.keys

		if (!newIds.contentEquals(previousIds)) {
			updateTreePanel(flatStructure)
		} else {
			flatStructure.forEach { ho ->
				val treeItem = componentRegistry.getValue(ho.id)
				updateContent(ho, treeItem)
			}
		}
	}

	// TODO разделить создание по каждому HO, ибо для каждого элемента набор элементов влияет на паддинги
	private fun updateTreePanel(structure: List<HierarchyObject>) {
		val newComponentCatalogue = LinkedHashMap<String, TreeItem>(componentRegistry.size)
		structure.forEach { ho ->
			val component = componentRegistry[ho.id] ?: makeTreeLine(ho)
			newComponentCatalogue[ho.id] = component
		}

		componentRegistry.clear()
		componentRegistry.putAll(newComponentCatalogue)

		val treeLines: List<Table> = structure.mapNotNull { ho ->
			val id = ho.id

			val depth = ho.idSplit.size
			val depthCoef = depth.coerceAtLeast(1) - 1
			val startShift = (8 + depthCoef * 16).toFloat()

			val component = componentRegistry[id] ?: return@mapNotNull null

			updateContent(ho, component)

			Table().left().top().apply {
				addCell(jiSpace()).padRight(startShift)

				component.asList().forEach { view ->
					val pad = view.pad.asFloatPad()
					addCell(view)
						.pad(pad.top, pad.left, pad.bottom, pad.right)

				}
			}
		}

		panel.removeAll()
		treeLines.forEach { treeLine ->
			panel.add(treeLine)
		}
	}

	private fun makeTreeLine(ho: HierarchyObject): TreeItem {
		val front = makeFrontText()
		val back = makeBackText()
		val main = makeMainComponent(ho)
		val actions = makeActions(ho)

		return TreeItem(main = main, actions = actions, front = front, back = back)
	}

	private fun updateContent(ho: HierarchyObject, treeItem: TreeItem) {
		treeItem.front.let { component ->
			val frontText = ho.attributeOrNull<FrontText>()?.text.asString()
			if (frontText.isNullOrEmpty()) {
				component.isVisible = false
			} else {
				component.isVisible = true
				component.text = frontText
			}

		}

		treeItem.back.let { component ->
			val backText = ho.attributeOrNull<BackText>()?.text.asString()
			if (backText.isNullOrEmpty()) {
				component.isVisible = false
			} else {
				component.isVisible = true
				component.text = backText
			}
		}

		// TODO Обновлять Actions. Нужно менять структуру из TreeItem, Table...

		treeItem.apply {
			when (ho) {
				is HOTreeCheck -> {
					main as JCheckBox
					main.setSelectedIfDiffer(ho.selected)

					front.pad = if (front.isVisible) Pad(right = 6) else Pad.ZERO
					main.pad = Pad(top = -1, bottom = -3)
					back.pad = if (back.isVisible) Pad(left = 6, right = 2) else Pad.ZERO
					actions.forEach { it.pad = Pad(left = 4) }
				}

				is HOFile      -> {
					main as JTextField
					main.setTextIfDiffers(ho.text)

					front.pad = if (front.isVisible) Pad(right = 6) else Pad.ZERO
					actions.forEach { it.pad = Pad(left = 4) }
					back.pad = if (back.isVisible) Pad(left = 7, right = 2) else Pad.ZERO
				}

				is HOLabel     -> {
					front.pad = if (front.isVisible) Pad(right = 6) else Pad.ZERO
					actions.forEach { it.pad = Pad(left = 4) }
					back.pad = if (back.isVisible) Pad(left = 7, right = 2) else Pad.ZERO
				}

				is HOSelector  -> {
					main as JComboBox<String>

					if (!compareItems(ho.items, main.model)) {
						main.setItems(ho.items.toTypedArray(), ho.selectedIndex)
					} else {
						main.setSelectedIndexIfDiffer(ho.selectedIndex)
					}

					front.pad = if (front.isVisible) Pad(right = 2) else Pad.ZERO
					main.pad = Pad(top = 2, left = 4)
					back.pad = if (back.isVisible) Pad(left = 2, right = 2) else Pad.ZERO
					actions.forEach { it.pad = Pad(left = 4) }
				}
			}
		}

		val isEnabled = !hasUncheckedParent(ho)
		treeItem.asList().asSequence().filterNotNull().forEach { it.isEnabled = isEnabled }
	}

	private fun hasUncheckedParent(ho: HierarchyObject): Boolean =
		ho.hasParent { (it as? HOTreeCheck)?.selected == false }

	private fun Text?.asString(): String? =
		this?.let { viewModel.texts[this] }

	private fun makeFrontText(): JLabel =
		jiLabel("")

	private fun makeBackText(): JLabel =
		jiLabel("")

	private fun makeActions(ho: HierarchyObject): List<JIPlainButton> =
		ho.actions.map { act ->
			makeAction(act).apply {
				addOnClickListener { viewModel.handleAction(ho, act) }
			}
		}

	private fun makeMainComponent(ho: HierarchyObject): JComponent =
		when (ho) {
			is HOTreeCheck -> makeTreeCheckComponent(ho).apply { addOnCheckListener { isSelected -> viewModel.handleCheckChange(ho, isSelected) } }
			is HOFile      -> makeFileComponent(ho).apply { addTextChangeListener { txt -> viewModel.handleTextChange(ho, txt) } }
			is HOLabel     -> makeLabelComponent(ho)
			is HOSelector  -> makeSelectorComponent(ho).apply { addOnSelectListener { item -> viewModel.handleSelectorChange(ho, item) } }
		}
}