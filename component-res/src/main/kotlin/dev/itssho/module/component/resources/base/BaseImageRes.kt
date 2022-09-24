package dev.itssho.module.component.resources.base

import java.awt.Dimension
import java.awt.Image
import javax.swing.Icon

sealed interface BaseImageRes {

	fun asIcon(size: Dimension? = null): Icon

	fun asImage(size: Dimension? = null): Image

	val img16px: Image get() = asImage(PX_16)

	val ic16px: Icon get() = asIcon(PX_16)
	val ic12px: Icon get() = asIcon(PX_12)
}