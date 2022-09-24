package dev.itssho.module.hierarchy.extension

import dev.itssho.module.hierarchy.HierarchyObject

val HierarchyObject.HOSelector.selectedItem: String
	get() = this.items[this.selectedIndex]