package dev.itssho.module.qpay.module.structure.ui.delegate

import dev.itssho.module.core.presentation.ViewModel
import dev.itssho.module.hierarchy.Act
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.extension.addItem
import dev.itssho.module.hierarchy.extension.checkActionsUniqueness
import dev.itssho.module.hierarchy.extension.checkAttributesUniqueness
import dev.itssho.module.hierarchy.extension.checkHierarchyIdsUniqueness
import dev.itssho.module.hierarchy.extension.checkPersonalIdsChars
import dev.itssho.module.hierarchy.extension.deleteItem
import dev.itssho.module.hierarchy.extension.forEach
import dev.itssho.module.hierarchy.extension.setCheck
import dev.itssho.module.hierarchy.extension.setSelectedItem
import dev.itssho.module.hierarchy.extension.setText
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.hierarchy.text.Text
import dev.itssho.module.hierarchy.text.Textual
import dev.itssho.module.qpay.module.common.domain.usecase.GetTextUseCase
import dev.itssho.module.qpay.module.structure.domain.usecase.GenerateUniqueIdUseCase
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelState.Content
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelState.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TreePanelViewModel(
	private val moduleAction: ModuleAction,
	private val valueStorage: ValueStorage,
	private val generateUniqueIdUseCase: GenerateUniqueIdUseCase,
	private val getTextUseCase: GetTextUseCase,
) : ViewModel() {

	private val _texts: HashMap<Text, String> = hashMapOf()
	val texts: Map<Text, String> = _texts

	private val _state = MutableStateFlow<TreePanelState>(Loading)
	val state: StateFlow<TreePanelState> = _state

	fun loadStructure() {
		val hierarchyInitializer = moduleAction.hierarchyInitializer

		val ho = hierarchyInitializer.initialize(valueStorage)

		checkHierarchyIdsUniqueness(ho)
		checkPersonalIdsChars(ho)
		checkAttributesUniqueness(ho)
		checkActionsUniqueness(ho)

		fillTexts(ho)
		_state.value = Content(ho)
	}

	fun handleCheckChange(ho: HierarchyObject.HOTreeCheck, selected: Boolean) {
		val currentState = (state.value as? Content)?.structure ?: return
		val newStructure = currentState.setCheck(ho.id, selected)
		_state.value = Content(newStructure)
	}

	fun handleTextChange(ho: HierarchyObject.HOFile, text: String) {
		val currentState = (state.value as? Content)?.structure ?: return
		val newStructure = currentState.setText(ho.id, text)
		_state.value = Content(newStructure)
	}

	fun handleSelectorChange(ho: HierarchyObject.HOSelector, item: String) {
		val currentState = (state.value as? Content)?.structure ?: return
		val newStructure = currentState.setSelectedItem(ho.id, item)
		_state.value = Content(newStructure)
	}

	fun handleAction(ho: HierarchyObject, act: Act) {
		val structure = state.value.asContent()?.structure!!
		require(ho.actions.contains(act)) { "$act is no applicable to ${ho.toSimpleString()}" }

		val newStructure = when (act) {
			Act.DELETE     -> structure.deleteItem(ho)!! // TODO добавить норм обработку удаления корневого элемента
			Act.ADD_FILE   -> structure.addItem(ho.id, HierarchyObject.HOFile(generateUniqueIdUseCase(), "", emptyList(), emptyList()))
			Act.ADD_FOLDER -> structure.addItem(ho.id, HierarchyObject.HOFile(generateUniqueIdUseCase(), "", emptyList(), emptyList()))
		}

		_state.value = Content(newStructure)
	}

	private fun fillTexts(rootHo: HierarchyObject) {
		rootHo.forEach { ho ->
			ho.attrs.forEach { attr ->
				if (attr is Textual) {
					val text = getTextUseCase(attr.text)
					_texts[attr.text] = text
				}
			}
		}
	}
}
