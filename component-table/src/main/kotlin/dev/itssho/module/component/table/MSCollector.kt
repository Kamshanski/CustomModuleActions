package dev.itssho.module.component.table

import org.jdesktop.swingx.JXMultiSplitPane
import org.jdesktop.swingx.MultiSplitLayout
import org.jdesktop.swingx.MultiSplitLayout.*
import javax.swing.JComponent

internal enum class Orientation {
	HORIZONTAL, VERTICAL
}

sealed class MSCollector(private val orientation: Orientation, items: Array<out Any>) : MSNode {
	val msNodes = items
		.mapNotNull {
			when (it) {
				is JComponent -> MSLeaf(it)
				is MSNode     -> it
				else          -> null
			}
		}
		.addDividers(MSDivider)

	fun extractComponents(): Map<String, JComponent> = msNodes
		.mapNotNull { node ->
			when (node) {
				is MSCollector -> node.extractComponents().toList()
				MSDivider      -> null
				is MSLeaf      -> listOf(node.name to node.component)
			}
		}
		.flatten()
		.toMap()

	fun toSplit(): Split {
		val split = Split()
		split.isRowLayout = orientation == Orientation.HORIZONTAL

		val list = msNodes.map { node ->
			when (node) {
				is MSCollector -> node.toSplit()
				MSDivider      -> Divider()
				is MSLeaf      -> Leaf(node.toString())
			}
		}

		split.setChildren(list)

		return split
	}

	fun buildPane(): JXMultiSplitPane {
		val rootSplit = toSplit()
		val layout = MultiSplitLayout(rootSplit)

		val pane = JXMultiSplitPane()

		val components = extractComponents()

		pane.layout = layout
		for ((name, component) in components) {
			pane.add(component, name)
		}

		return pane
	}
}

class MSRow(vararg items: Any) : MSCollector(Orientation.HORIZONTAL, items)

class MSColumn(vararg items: Any) : MSCollector(Orientation.VERTICAL, items)