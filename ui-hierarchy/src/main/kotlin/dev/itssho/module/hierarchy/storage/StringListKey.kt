package dev.itssho.module.hierarchy.storage

object StringListKey {

	val MODULE_NAME = "MODULE_NAME"

	@Deprecated("Хз зачем это может быть нужно")
	val PROJECT_PATH = "PROJECT_PATH"

	// TODO заменить рефлексией
	fun values(): List<String> = listOf(
		MODULE_NAME,
		PROJECT_PATH,
	)
}