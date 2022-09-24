package dev.itssho.module.core.actor

import dev.itssho.module.core.ui.UiPlatform
import org.jetbrains.kotlin.utils.addToStdlib.cast

fun UiPlatform.JET_BRAINS.requireContext(context: Context): JBContext = context.cast()
fun UiPlatform.SWING.requireContext(context: Context): SwingContext = context.cast()