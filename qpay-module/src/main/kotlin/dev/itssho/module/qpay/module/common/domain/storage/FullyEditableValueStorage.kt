package dev.itssho.module.qpay.module.common.domain.storage

import dev.itssho.module.hierarchy.storage.ConstValueReplacementException
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage.Str
import dev.itssho.module.hierarchy.storage.ValueStorage.StrList

class FullyEditableValueStorage : MutableValueStorage {

	val stringConstantsKeys = Str.values()
	val listConstantsKeys = StrList.values()

	private val strings: MutableMap<String, String> = HashMap()
	private val lists: MutableMap<String, List<String>> = HashMap()

	override fun put(key: String, string: String): Boolean {
		if (strings.containsKey(key)) {
			return false
		}

		strings[key] = string

		return true
	}

	override fun put(key: String, list: List<String>): Boolean {
		if (lists.containsKey(key)) {
			return false
		}

		lists[key] = list

		return true
	}

	override fun putOrReplace(key: String, string: String) {
		if (stringConstantsKeys.contains(key)) {
			throw ConstValueReplacementException("Cannot replace const String value with key '$key'")
		}

		strings[key] = string
	}

	override fun putOrReplace(key: String, list: List<String>) {
		if (listConstantsKeys.contains(key)) {
			throw ConstValueReplacementException("Cannot replace const List<String> value with key '$key'")
		}

		lists[key] = list
	}

	override fun get(key: String): String? = strings[key]

	override fun getList(key: String): List<String>? = lists[key]

	fun forcePut(key: String, string: String) {
		strings[key] = string
	}

	fun forcePut(key: String, list: List<String>) {
		lists[key] = list
	}
}