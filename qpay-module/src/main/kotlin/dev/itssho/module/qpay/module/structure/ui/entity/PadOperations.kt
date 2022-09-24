package dev.itssho.module.qpay.module.structure.ui.entity

operator fun Pad.plus(other: Pad): Pad =
	add(other.top, other.bottom, other.left, other.left)