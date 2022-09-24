package dev.itssho.module.hierarchy.extension

import dev.itssho.module.hierarchy.HierarchyObject
import kotlin.reflect.KClass

/**
 * Order from top to bottom, from first child to last child
 * @sample [Top > Top.A1 > Top.A1.B1 > Top.A1.B2 > Top.A2 > Top.A2.B1 > Top.A2.B1.C1 > Top.A3]
 *      Top
 *       |
 *    ----------
 *    |     |  |
 *   A1    A2 A3
 *   |      |
 *  ----    |
 *  |  |    |
 * B1 B2   B1
 *          |
 *         C1
 */
fun HierarchyObject.flatten(): List<HierarchyObject> {
	val hierarchyObjects = ArrayList<HierarchyObject>()
	this.forEach { hierarchyObject -> hierarchyObjects.add(hierarchyObject) }
	return hierarchyObjects
}

fun HierarchyObject.forEach(action: (HierarchyObject) -> Unit) {
	action(this)
	if (this.children.isNotEmpty()) {
		for (child in this.children) {
			child.forEach(action)
		}
	}
}

fun HierarchyObject.hasChild(predicate: (child: HierarchyObject) -> Boolean): Boolean {
	children.forEach { child ->
		if (predicate(child)) {
			return true
		}
		if (child.hasChild(predicate)) {
			return true
		}
	}

	return false
}

fun HierarchyObject.childByIdOrNull(id: String): HierarchyObject? {
	for (child in children) {
		if (child.id == id) {
			return child
		}

		val childChild = child.childByIdOrNull(id)
		if (childChild != null) {
			return childChild
		}
	}

	return null
}

inline fun <reified T: HierarchyObject> HierarchyObject.childByIdAndTypeOrNull(id: String): T? = childByIdAndTypeOrNull(T::class, id)

fun <T: HierarchyObject> HierarchyObject.childByIdAndTypeOrNull(clazz: KClass<T>, id: String): T? {
	for (child in children) {
		if (child::class == clazz && child.id == id) {
			return child as T
		}

		val childChild = child.childByIdAndTypeOrNull(clazz, id)
		if (childChild != null) {
			return childChild
		}
	}

	return null
}
