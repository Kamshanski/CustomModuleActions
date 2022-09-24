package dev.itssho.module.component.resources.base

import delegate.weakLazy
import dev.itssho.module.component.resources.getResUrl
import dev.itssho.module.component.resources.loadIcon
import java.awt.Dimension
import java.awt.Image
import java.util.*
import javax.swing.Icon
import javax.swing.ImageIcon

open class PixelImage internal constructor(resPath: String) : BaseImageRes {

	private val originalImage by weakLazy { loadIcon(getResUrl(resPath)) }
	private val imageCache = WeakHashMap<Dimension, Image>(2)
	private val iconCache = WeakHashMap<Dimension, Icon>(2)

	override fun asIcon(size: Dimension?): Icon {
		val selectedSize = size ?: PX_16
		return iconCache.computeIfAbsent(selectedSize) { s -> ImageIcon(asImage(s)) }
	}

	override fun asImage(size: Dimension?): Image {
		val selectedSize = size ?: PX_16
		return imageCache.computeIfAbsent(selectedSize) { s -> originalImage.getScaledInstance(s.width, s.height, Image.SCALE_DEFAULT) }
	}
}