package dev.itssho.module.core.actor

import dev.itssho.module.core.ui.UiPlatform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing
import javax.swing.JFrame

class SwingContext(val frame: JFrame): Context {

	override val platform: UiPlatform = UiPlatform.SWING

	override val global: MutableMap<Any, Any> = mutableMapOf()
//	override val scope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Swing)
}