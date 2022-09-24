package dev.itssho.module.hierarchy.extension

import contains
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.attr.Attr

inline fun HierarchyObject.hasAttribute(predicate: (Attr) -> Boolean): Boolean =
	this.attrs.contains(predicate)

inline fun <reified T: Attr> HierarchyObject.hasAttribute(): Boolean =
	this.attrs.contains { it is T }

inline fun <reified T: Attr> HierarchyObject.attributeOrNull(): T? =
	this.attrs.firstOrNull { it is T } as? T

inline fun HierarchyObject.attributeOrNull(predicate: (Attr) -> Boolean): Attr? =
	this.attrs.firstOrNull(predicate)