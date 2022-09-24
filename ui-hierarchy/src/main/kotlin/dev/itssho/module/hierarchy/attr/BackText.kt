package dev.itssho.module.hierarchy.attr

import dev.itssho.module.hierarchy.text.Text
import dev.itssho.module.hierarchy.text.Textual

data class BackText(override val text: Text) : Attr.StandardAttr(), Textual