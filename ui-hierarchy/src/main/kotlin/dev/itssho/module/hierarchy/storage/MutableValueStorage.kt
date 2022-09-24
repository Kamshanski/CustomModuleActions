package dev.itssho.module.hierarchy.storage

import kotlin.jvm.Throws

// TODO сделать ключи объектами, чтобы не просить список по ключу строки
interface ValueStorage {

	fun get(key: String): String?

	fun getList(key: String): List<String>?
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