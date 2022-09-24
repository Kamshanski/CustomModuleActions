package dev.itssho.module.hierarchy.attr

abstract class Attr internal constructor(){

	abstract val attrId: String

	open class CustomAttr(override val attrId: String): Attr()

	sealed class StandardAttr: Attr() {
		final override val attrId: String = this::class.simpleName!!
	}

	fun equalsAttr(other: Attr?): Boolean {
		if (this === other) return true

		if (attrId != other?.attrId) return false

		return true
	}
}
