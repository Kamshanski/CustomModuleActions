package dev.itssho.module.resources

import com.intellij.util.ui.EmptyIcon
import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import javax.swing.Icon
import javax.swing.ImageIcon

object Icons {
    val qpay: Icon by lazy {
        val img = loadIcon(url = getResUr("/drawable/QPAY.bmp"))
        val img16px = img.getScaledInstance(16, 16, Image.SCALE_DEFAULT)
        ImageIcon(img16px)
    }

    val test: Icon by lazy {
        val icon = BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB)
        val g = icon.createGraphics()
        g.apply {
            color = Color.CYAN
            fillRect(0,0, 16, 16)
            color = Color.YELLOW
            fillOval(2,2, 12, 12)
        }
        g.dispose()
        ImageIcon(icon)
    }

    val empty: Icon = EmptyIcon.ICON_16
}