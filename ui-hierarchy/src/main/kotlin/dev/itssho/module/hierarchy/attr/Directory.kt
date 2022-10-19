package dev.itssho.module.hierarchy.attr

import dev.itssho.module.hierarchy.HierarchyObject

data class Directory(val chain: List<Chain>) : Attr.StandardAttr() {
	constructor(vararg chain: Chain) : this(chain.toList())

	/** Directory chain */
	sealed interface Chain {

		/** @property delimiter на случай, если [chain] будет цепочкой из нескольких вложенных папок */
		data class CONST(val chain: List<String>) : Chain

		data class CALCULATED(val calculate: (HierarchyObject) -> List<String>) : Chain
	}
}