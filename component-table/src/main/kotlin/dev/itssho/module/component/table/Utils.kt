package dev.itssho.module.component.table

import isEven

fun <T> List<T>.addDividers(divider: T): List<T> = List((this.size * 2 - 1).coerceAtLeast(0)) {
	if (it.isEven()) {
		this[it / 2]
	} else {
		divider
	}
}
