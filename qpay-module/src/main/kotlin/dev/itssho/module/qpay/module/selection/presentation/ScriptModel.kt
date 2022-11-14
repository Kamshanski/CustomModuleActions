package dev.itssho.module.qpay.module.selection.presentation

sealed interface ScriptModel {

	val name: String
	val path: String

	class Failure(override val path: String, override val name: String, val exception: String, val timestamp: String) : ScriptModel

	class Loaded(override val path: String, override val name: String, val timestamp: String) : ScriptModel

	class Loading(override val path: String, override val name: String) : ScriptModel
}