package dev.itssho.module.hierarchy

import dev.itssho.module.hierarchy.attr.Attr
import kotlin.reflect.KProperty1

// TODO поле name выглядит бесполезным из-за атрибута FrontText. Стоит его удалить
sealed class HierarchyObject(
	val personalId: String,
	val attrs: List<Attr> = emptyList(),
	val actions: List<Act> = emptyList(),
	val children: List<HierarchyObject> = emptyList()
) {

	companion object {

		const val ID_DIVIDER = " "
	}

	var parent: HierarchyObject? = null
		private set

	init {
		if (personalId.contains(ID_DIVIDER)) {
			error("HierarchyObject id cannon contain '$ID_DIVIDER': '$personalId'")
		}
		children.forEach { child -> child.parent = this }
	}

	private val idWithNoParent by lazy { personalId }
	private val idWithParent by lazy { parent!!.id.let { it + ID_DIVIDER } + personalId }
	val id: String
		get() = if (parent != null) idWithParent else idWithNoParent

	private val idSplitWithNoParent by lazy { listOf(personalId) }
	private val idSplitWithParent by lazy { parent!!.idSplit + personalId }
	val idSplit: List<String>
		get() = if (parent != null) idSplitWithParent else idSplitWithNoParent

	class HOLabel(personalId: String, attributes: List<Attr> = emptyList(), actions: List<Act> = emptyList(), children: List<HierarchyObject> = emptyList()) :
		HierarchyObject(personalId, attributes, actions, children)

	class HOTreeCheck(
		personalId: String,
		val selected: Boolean,
		attrs: List<Attr> = emptyList(),
		actions: List<Act> = emptyList(),
		children: List<HierarchyObject> = emptyList()
	) : HierarchyObject(personalId, attrs, actions, children) {

		override fun equals(other: Any?): Boolean {
			if (this === other) return true
			if (javaClass != other?.javaClass) return false
			if (!super.equals(other)) return false

			other as HOTreeCheck

			if (selected != other.selected) return false

			return true
		}

		override fun hashCode(): Int {
			var result = super.hashCode()
			result = 31 * result + selected.hashCode()
			return result
		}
	}

	// Лучше переименовать в HOTextInput
	class HOFile(personalId: String, val text: String, attrs: List<Attr> = emptyList(), actions: List<Act> = emptyList()) : HierarchyObject(personalId, attrs, actions) {

		override fun equals(other: Any?): Boolean {
			if (this === other) return true
			if (other !is HOFile) return false
			if (!super.equals(other)) return false

			if (text != other.text) return false

			return true
		}

		override fun hashCode(): Int {
			var result = super.hashCode()
			result = 31 * result + text.hashCode()
			return result
		}

		// TODO для каждого сделать такую функицию
		fun copy(personalId: String = this.personalId, text: String = this.text, attrs: List<Attr> = this.attrs, actions: List<Act> = this.actions): HOFile =
			HOFile(personalId, text, attrs, actions)
	}

	// TODO: Мб потом добавиь селектору детей, чтобы переключать дерево под ним?
	class HOSelector(personalId: String, val items: List<String>, val selectedIndex: Int, attributes: List<Attr> = emptyList()) : HierarchyObject(personalId, attributes) {

		override fun equals(other: Any?): Boolean {
			if (this === other) return true
			if (other !is HOSelector) return false
			if (!super.equals(other)) return false

			if (items != other.items) return false
			if (selectedIndex != other.selectedIndex) return false

			return true
		}

		override fun hashCode(): Int {
			var result = super.hashCode()
			result = 31 * result + items.hashCode()
			result = 31 * result + selectedIndex
			return result
		}
	}


	fun toSimpleString(): String = "${this::class.java.simpleName}(${this.id})"

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is HierarchyObject) return false

		if (attrs != other.attrs) return false
		if (actions != other.actions) return false
		if (id != other.id) return false

		return true
	}

	override fun hashCode(): Int {
		var result = attrs.hashCode()
		result = 31 * result + actions.hashCode()
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