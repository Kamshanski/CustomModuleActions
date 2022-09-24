package dev.itssho.module.feature.qpay.module.common.presentation

import dev.itssho.module.util.JSON
import kotlin.reflect.KProperty1


sealed class HierarchyObject(val name: String, val id: String, val actions: List<Act> = emptyList(), val children: List<HierarchyObject> = emptyList()) {

    class HOLabel(name: String, id: String, actions: List<Act> = emptyList(), children: List<HierarchyObject> = emptyList())
        : HierarchyObject(name, id, actions, children)

    class HOFinalCheck(name: String, id: String, val isSelected: Boolean, actions: List<Act> = emptyList(), children: List<HierarchyObject> = emptyList())
        : HierarchyObject(name, id, actions, children)

    class HOTreeCheck(name: String, id: String, val isSelected: Boolean, actions: List<Act> = emptyList(), children: List<HierarchyObject> = emptyList())
        : HierarchyObject(name, id, actions, children)

    class HOPlainFolder(name: String, id: String, actions: List<Act> = emptyList(), children: List<HierarchyObject> = emptyList())
        : HierarchyObject(name, id, actions, children)

    class HOFile(name: String, id: String, val text: String)
        : HierarchyObject(name, id)

    class HOSelector(name: String, id: String, val items: List<String>, val selectedIndex: Int)
        : HierarchyObject(name, id)

    override fun toString(): String {
        return JSON.toJson(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HierarchyObject) return false

        if (name != other.name) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}

fun KProperty1<*, *>.convertProperty(holder: Any): String {
    val value = call(holder)
    val str = if (value is Iterable<*>) {
        value.joinToString(prefix = "[", postfix = "]")
    } else {
        value.toString()
    }
    return "'$name'='${str}'"
}