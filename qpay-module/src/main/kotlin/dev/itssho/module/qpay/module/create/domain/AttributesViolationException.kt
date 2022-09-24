package dev.itssho.module.qpay.module.create.domain

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.attr.Attr

class AttributesViolationException(arguments: List<Attr>, message: String? = null): Throwable("${message ?: "Invalid Attributes"}: ${arguments.joinToString()}") {
	constructor(argument: Attr, message: String? = null): this(listOf(argument), message)
	constructor(ho: HierarchyObject, message: String? = null): this(ho.attrs, message ?: "Invalid Attributes in '${ho.id}'")
}