package dev.itssho.module.qpay.module.structure.presentation

import dev.itssho.module.hierarchy.HierarchyObject

interface QpayStructureRouter {

	fun close()

	fun proceed(structure: HierarchyObject)
}