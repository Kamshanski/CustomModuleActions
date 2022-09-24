package dev.itssho.module.resources

import com.intellij.util.ui.EmptyIcon
import java.awt.Image
import javax.swing.Icon
import javax.swing.ImageIcon

object Icons {
    val qpay: Icon by lazy {
        val img = loadIcon(url = getResUr("/drawable/QPAY.bmp"))
        val img16px = img.getScaledInstance(16, 16, Image.SCALE_DEFAULT)
        ImageIcon(img16px)
    }

    val empty: Icon = EmptyIcon.ICON_16
}