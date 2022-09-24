package dev.itssho.module.qpay.module.structure.ui.delegate

import com.esotericsoftware.tablelayout.Cell
import com.esotericsoftware.tablelayout.swing.Table
import dev.itssho.module.qpay.module.structure.ui.entity.FloatPad
import dev.itssho.module.qpay.module.structure.ui.entity.Pad
import swing.getProperty
import swing.setProperty
import javax.swing.JComponent


fun Table.addCellWithPad(component: JComponent): Cell<*> = addCell(component, component.pad.asFloatPad())
fun Table.addCell(component: JComponent, pad: Pad): Cell<*> = addCell(component, pad.asFloatPad())

private fun Table.addCell(component: JComponent, pad: FloatPad): Cell<*> =
	addCell(component).pad(pad.top, pad.left, pad.bottom, pad.right)


internal val PAD_KEY = "dev.itssho.module.PAD_KEY"
internal var JComponent.pad: Pad
	get() = getProperty(PAD_KEY) ?: Pad()
	set(value) = setProperty(PAD_KEY, value)