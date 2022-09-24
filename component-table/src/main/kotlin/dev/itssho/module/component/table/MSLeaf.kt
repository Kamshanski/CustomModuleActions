package dev.itssho.module.component.table

import javax.swing.JComponent

class MSLeaf(val component: JComponent) : MSNode {
	val name: String get() = toString()
}