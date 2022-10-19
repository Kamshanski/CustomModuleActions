package dev.itssho.module.qpay.module.create.domain

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.attr.Directory

class DirectoryAttributeViolationException(arguments: List<Directory.Chain>, message: String? = null): Throwable("${message ?: "Invalid DirChain arguments"}: ${arguments.joinToString()}") {
	constructor(argument: Directory.Chain, message: String? = null): this(listOf(argument), message)
	constructor(ho: HierarchyObject, message: String? = null): this(ho.attrs.filterIsInstance<Directory.Chain>(), message ?: "Invalid DirChain arguments in '${ho.id}'")
}