package dev.itssho.module.service.preferences.data.persistent

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun string(default: String, mapSrc: MutableMap<String, String>) = StringDelegate(default, mapSrc)
fun boolean(default: Boolean, mapSrc: MutableMap<String, String>) = BoolDelegate(default, mapSrc)

class BoolDelegate(private val default: Boolean, private val mapSrc: MutableMap<String, String>) : ReadWriteProperty<Any?, Boolean> {

	override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean =
		mapSrc[property.name]?.toBoolean() ?: default.also {
		setValue(thisRef, property, default)
	}

	override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
		mapSrc[property.name] = value.toString()
	}
}

class StringDelegate(private val default: String, private val mapSrc: MutableMap<String, String>) : ReadWriteProperty<Any?, String> {

	override fun getValue(thisRef: Any?, property: KProperty<*>): String =
		mapSrc[property.name] ?: default.also {
			setValue(thisRef, property, default)
		}

	override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
		mapSrc[property.name] = value
	}
}