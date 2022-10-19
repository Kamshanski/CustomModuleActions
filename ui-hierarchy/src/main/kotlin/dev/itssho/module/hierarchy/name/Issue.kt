package dev.itssho.module.hierarchy.name

sealed interface Issue {

	val message: String

	data class Error(override val message: String) : Issue

	data class Warning(override val message: String) : Issue
}