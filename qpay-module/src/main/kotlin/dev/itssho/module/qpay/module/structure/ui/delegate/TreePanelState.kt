package dev.itssho.module.qpay.module.structure.ui.delegate

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.text.Text

sealed interface TreePanelState {

	class Content(val structure: HierarchyObject, val texts: Map<Text, String>) : TreePanelState

	object Loading : TreePanelState
}