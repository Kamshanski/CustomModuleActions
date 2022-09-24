package dev.itssho.module.core.ui

sealed interface UiPlatform {
	object JET_BRAINS : UiPlatform
	object SWING : UiPlatform
}