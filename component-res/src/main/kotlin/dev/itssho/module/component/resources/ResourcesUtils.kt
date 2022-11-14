package dev.itssho.module.component.resources

import java.awt.Color
import java.awt.Image
import java.net.URL
import javax.imageio.ImageIO


fun <T: Any> T.getResUrl(resPath: String): URL {
	if (!resPath.startsWith("/")) {
		throw IllegalArgumentException("Resource path must start with '/'")
	}

	val clazz = this.javaClass
	val resUrl = clazz.getResource(resPath)
	return resUrl
}

fun loadIcon(url: URL): Image = ImageIO.read(url)

fun Color.opaque(alpha: Int): Color = Color(red, green, blue, alpha)