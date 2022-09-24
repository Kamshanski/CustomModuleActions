package dev.itssho.module.hierarchy.text

sealed interface Text {

	abstract class Var: Text {

		object MODULE_NAME : Var()
	}

	data class Const(val string: String) : Text

	class Complex(vararg val text: Text) : Text {

		override fun equals(other: Any?): Boolean {
			if (this === other) return true
			if (javaClass != other?.javaClass) return false

			other as Complex

			if (!text.contentEquals(other.text)) return false

			return true
		}

		override fun hashCode(): Int {
			return text.contentHashCode()
		}
	}
}