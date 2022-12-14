package dev.itssho.module.qpay.module.structure.actor

import dev.itssho.module.hierarchy.HierarchyObject

sealed interface QpayStructureStepResult {

	object Nothing : QpayStructureStepResult

	data class Structure(val filesFoldersHierarchy: HierarchyObject) : QpayStructureStepResult
}