package dev.itssho.module.hierarchy.extension

import contains
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.HierarchyObject.*
import dev.itssho.module.hierarchy.attr.Attr
import replaceAll

fun HierarchyObject.deleteItem(objectToDelete: HierarchyObject): HierarchyObject? =
	if (objectToDelete == this) {
		null
	} else {
		when (this) {
			is HOFile      -> this
			is HOSelector  -> this
			is HOLabel     -> HOLabel(personalId, attrs, actions, children.mapNotNull { it.deleteItem(objectToDelete) })
			is HOTreeCheck -> HOTreeCheck(personalId, selected, attrs, actions, children.mapNotNull { it.deleteItem(objectToDelete) })
		}
	}

fun HierarchyObject.addItem(insertId: String, objectToAdd: HierarchyObject): HierarchyObject =
	when (this) {
		is HOFile      -> this
		is HOSelector  -> this
		is HOLabel     -> HOLabel(personalId, attrs, actions, if (id == insertId) children + objectToAdd else children)
		is HOTreeCheck -> HOTreeCheck(personalId, selected, attrs, actions, if (id == insertId) children + objectToAdd else children)
	}


fun HierarchyObject.setCheck(treeCheckId: String, check: Boolean): HierarchyObject =
	when (this) {
		is HOFile      -> this
		is HOSelector  -> this
		is HOLabel     -> HOLabel(personalId, attrs, actions, children.map { it.setCheck(treeCheckId, check) })
		is HOTreeCheck -> {
			val id = this.id
			val newCheck = if (id == treeCheckId)
				check
			else
				this.selected
			HOTreeCheck(personalId, newCheck, attrs, actions, children.map { it.setCheck(treeCheckId, check) })
		}
	}


fun HierarchyObject.setText(fileId: String, text: String): HierarchyObject =
	when (this) {
		is HOFile      -> {
			val newText = if (id == fileId) text else this.text
			this.copy(text = newText)
		}
		is HOSelector  -> this
		is HOLabel     -> HOLabel(personalId, attrs, actions, children.map { it.setText(fileId, text) })
		is HOTreeCheck -> HOTreeCheck(personalId, selected, attrs, actions, children.map { it.setText(fileId, text) })
	}

fun HierarchyObject.setSelectedItem(selectorId: String, item: String): HierarchyObject =
	when (this) {
		is HOFile      -> this
		is HOSelector  -> {
			val newItemIndex = items.indexOf(item)
			if (newItemIndex < 0 || selectedIndex == newItemIndex) {
				this
			} else {
				HOSelector(personalId, items, newItemIndex, attrs)
			}
		}
		is HOLabel     -> HOLabel(personalId, attrs, actions, children.map { it.setSelectedItem(selectorId, item) })
		is HOTreeCheck -> HOTreeCheck(personalId, selected, attrs, actions, children.map { it.setSelectedItem(selectorId, item) })
	}

enum class AddResolutionStrategy {
	THROW,
	SKIP,
	REWRITE,
}

fun HierarchyObject.addAttribute(changeId: String, attributeToAdd: Attr, resolutionStrategy: AddResolutionStrategy = AddResolutionStrategy.THROW): HierarchyObject {
	val newAttr = if (id == changeId && attrs.contains { it.equalsAttr(attributeToAdd) }) {
		when (resolutionStrategy) {
			AddResolutionStrategy.THROW   -> throw IllegalArgumentException("'$attributeToAdd' already exists in ${this.toSimpleString()}")
			AddResolutionStrategy.SKIP    -> attrs
			AddResolutionStrategy.REWRITE -> attrs.replaceAll(attributeToAdd, attributeToAdd) { thiz, other -> thiz.equalsAttr(other) }
		}
	} else {
		attrs
	}

	return when (this) {
		is HOFile      -> this.copy(attrs = newAttr)
		is HOSelector  -> HOSelector(personalId, items, selectedIndex, newAttr)
		is HOLabel     -> HOLabel(personalId, newAttr, actions, children.map { it.addAttribute(changeId, attributeToAdd, resolutionStrategy) })
		is HOTreeCheck -> HOTreeCheck(personalId, selected, newAttr, actions, children.map { it.addAttribute(changeId, attributeToAdd, resolutionStrategy) })
	}
}

fun HierarchyObject.filterSelected(): HierarchyObject? =
	when (this) {
		is HOFile      -> this.copy()
		is HOSelector  -> HOSelector(personalId, items, selectedIndex, attrs)
		is HOLabel     -> HOLabel(personalId, attrs, actions, children.mapNotNull { it.filterSelected() })
		is HOTreeCheck -> {
			if (selected) {
				HOTreeCheck(personalId, selected, attrs, actions, children.mapNotNull { it.filterSelected() })
			} else {
				null
			}
		}
	}