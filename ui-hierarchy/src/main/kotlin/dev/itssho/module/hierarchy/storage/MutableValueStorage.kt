package dev.itssho.module.hierarchy.storage

import kotlin.jvm.Throws

// TODO сделать ключи объектами, чтобы не просить список по ключу строки
interface ValueStorage {

	fun get(key: String): String?

	fun getList(key: String): List<String>?

	object StrList {

		val MODULE_NAME = "MODULE_NAME"

		@Deprecated("Хз зачем это может быть нужно")
		val PROJECT_PATH = "PROJECT_PATH"

		// TODO заменить рефлексией или enum
		fun values(): List<String> = listOf(
			MODULE_NAME,
			PROJECT_PATH,
		)
	}

	object Str {

		// TODO заменить рефлексией
		fun values(): List<String> = listOf(
		)
	}
}

interface MutableValueStorage : ValueStorage {

	@Throws(ConstValueReplacementException::class)
	fun put(key: String, string: String): Boolean

	@Throws(ConstValueReplacementException::class)
	fun putOrReplace(key: String, string: String)

	@Throws(ConstValueReplacementException::class)
	fun put(key: String, list: List<String>): Boolean

	@Throws(ConstValueReplacementException::class)
	fun putOrReplace(key: String, list: List<String>)
}
