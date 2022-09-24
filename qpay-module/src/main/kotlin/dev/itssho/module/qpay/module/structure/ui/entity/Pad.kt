package dev.itssho.module.qpay.module.structure.ui.entity

data class Pad(val top: Int = 0, val bottom: Int = 0, val left: Int = 0, val right: Int = 0) {

	companion object {

		val ZERO = Pad()
	}

	fun add(top: Int = 0, bottom: Int = 0, left: Int = 0, right: Int = 0): Pad =
		Pad(this.top + top, this.bottom + bottom, this.left + left, this.right + right)

	fun asFloatPad() = FloatPad(top.toFloat(), bottom.toFloat(), left.toFloat(), right.toFloat())
}