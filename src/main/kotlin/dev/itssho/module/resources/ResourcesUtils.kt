package dev.itssho.module.resources

import java.awt.Color
import java.awt.Image
import java.net.URL
import javax.imageio.ImageIO


fun <T: Any> T.getResUr(resPath: String): URL = this.javaClass.getResource(resPath)

fun loadIcon(url: URL): Image = ImageIO.read(url)

fun Color.opaque(alpha: Int): Color = Color(red, green, blue, alpha)