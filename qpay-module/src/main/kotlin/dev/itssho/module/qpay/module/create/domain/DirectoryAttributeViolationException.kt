package dev.itssho.module.qpay.module.create.domain

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.attr.DirChain

class DirectoryAttributeViolationException(arguments: List<DirChain.Dir>, message: String? = null): Throwable("${message ?: "Invalid DirChain arguments"}: ${arguments.joinToString()}") {
	constructor(argument: DirChain.Dir, message: String? = null): this(listOf(argument), message)
	constructor(ho: HierarchyObject, message: String? = null): this(ho.attrs.filterIsInstance<DirChain.Dir>(), message ?: "Invalid DirChain arguments in '${ho.id}'")
}