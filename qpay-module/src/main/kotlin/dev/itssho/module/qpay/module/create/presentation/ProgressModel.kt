package dev.itssho.module.qpay.module.create.presentation

import dev.itssho.module.hierarchy.HierarchyObject
import kotlin.reflect.KClass

class ProgressModel(
	val percent: Int,
	val itemType: KClass<out HierarchyObject>?,
	val itemName: String?,
)