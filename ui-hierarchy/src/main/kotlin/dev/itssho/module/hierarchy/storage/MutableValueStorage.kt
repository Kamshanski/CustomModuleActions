package dev.itssho.module.hierarchy.storage

// TODO сделать ключи объектами, чтобы не просить список по ключу строки
interface ValueStorage {

	@Throws(NoSuchElementException::class)
	fun get(key: String): String

	@Throws(NoSuchElementException::class)
	fun getList(key: String): List<String>

	fun getOrNull(key: String): String?

	fun getListOrNull(key: String): List<String>?

	object StrList {
		@Deprecated("Хз зачем это может быть нужно")
		val PROJECT_PATH = "PROJECT_PATH"

		// TODO заменить рефлексией или enum
		fun values(): List<String> = listOf(
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
