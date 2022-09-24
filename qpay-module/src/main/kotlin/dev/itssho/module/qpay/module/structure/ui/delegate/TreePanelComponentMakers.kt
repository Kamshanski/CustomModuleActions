package dev.itssho.module.qpay.module.structure.ui.delegate

import dev.itssho.module.component.components.button.JIPlainButton
import dev.itssho.module.component.resources.Icons
import dev.itssho.module.hierarchy.Act
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.ui.util.constructor.*
import string.times

private val MAX_TEXT_FIELD_WIDTH_TEXT = "M" * 50

fun makeTreeCheckComponent(ho: HierarchyObject.HOTreeCheck) =
	jCheckBox("", ho.selected).apply {
		isFocusPainted = false
	}

fun makeSelectorComponent(ho: HierarchyObject.HOSelector) =
	jSelector(ho.items, ho.selectedIndex)

fun makeLabelComponent(ho: HierarchyObject.HOLabel) =
	jiLabel("")

fun makeFileComponent(ho: HierarchyObject.HOFile) =
	jiTextField(MAX_TEXT_FIELD_WIDTH_TEXT, editable = true).apply {
		text = ho.text
	}

fun makeAction(action: Act): JIPlainButton =
	when (action) {
		Act.DELETE     -> jiPlainButton(Icons.DELETE.ic12px)
		Act.ADD_FILE   -> jiPlainButton(Icons.ADD_FILE.ic12px)
		Act.ADD_FOLDER -> jiPlainButton(Icons.ADD_FOLDER.ic12px)
	}