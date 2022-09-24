package dev.itssho.module.qpay.module.structure.presentation

import dev.itssho.module.hierarchy.HierarchyObject

sealed interface QpayStructureNavAction {

	object Nowhere: QpayStructureNavAction

	object Close: QpayStructureNavAction

	data class Continue(val structure: HierarchyObject): QpayStructureNavAction
}