package dev.itssho.module.hierarchy.extension

import dev.itssho.module.hierarchy.HierarchyObject

fun HierarchyObject.hasParent(): Boolean = this.parent != null

fun HierarchyObject.hasParent(predicate: (parent: HierarchyObject) -> Boolean): Boolean {
	val parent = this.parent ?: return false
	if (predicate(parent)) {
		return true
	} else {
		return parent.hasParent(predicate)
	}
}

fun HierarchyObject.forEachParent(action: (parent: HierarchyObject) -> Unit) {
	val parent = this.parent
	if (parent != null) {
		action(parent)
		parent.forEachParent(action)
	}
}

fun HierarchyObject.forEachParentWhile(takePredicate: (HierarchyObject) -> Boolean) {
	val parent = this.parent ?: return

	if (!takePredicate(parent)) {
		return
	}

	parent.forEachParentWhile(takePredicate)
}

//fun HierarchyObject.takeUntilParent(excludedThisObject: Boolean = false, stopPredicate: (parent: HierarchyObject) -> Boolean): List<HierarchyObject> {
//	val parents = ArrayList<HierarchyObject>()
//
//
//}

fun HierarchyObject.takeUtillParent(orderFromChildToParent: Boolean = true, excludeChild: Boolean = false, stopPredicate: (parent: HierarchyObject) -> Boolean): List<HierarchyObject> {
	val parents = ArrayList<HierarchyObject>()

	if (!excludeChild) {
		parents.add(this)
	}

	this.forEachParentWhile { candidate ->
		if (stopPredicate(candidate)) {
			return@forEachParentWhile false
		}

		parents.add(candidate)
		return@forEachParentWhile true
	}

	if (!orderFromChildToParent) {
		parents.reverse()
	}

	return parents
}