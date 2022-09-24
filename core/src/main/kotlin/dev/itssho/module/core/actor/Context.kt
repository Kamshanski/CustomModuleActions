package dev.itssho.module.core.actor

import dev.itssho.module.core.ui.UiPlatform
import kotlinx.coroutines.CoroutineScope

interface Context {

	val platform: UiPlatform

	// TODO TO BE DELETED AFTER DI
	val global: MutableMap<Any, Any>

//	val scope: CoroutineScope
}