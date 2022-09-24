package dev.itssho.module.qpay.module.structure.ui.delegate

import dev.itssho.module.hierarchy.HierarchyObject

sealed interface TreePanelState {

	class Content(val structure: HierarchyObject) : TreePanelState

	object Loading : TreePanelState
}