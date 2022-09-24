import com.esotericsoftware.tablelayout.Cell
import com.esotericsoftware.tablelayout.swing.Stack
import com.esotericsoftware.tablelayout.swing.Table
import com.intellij.ui.tabs.layout.singleRowLayout.SingleRowLayout
import dev.itssho.module.uikit2.component.text.XLabel
import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.uikit2.modify.modification.*
import dev.itssho.module.uikit2.util.swing.addTo
import dev.itssho.module.component.value.Gravity
import dev.itssho.module.component.value.MATCH_PARENT
import dev.itssho.module.component.value.Orientation
import dev.itssho.module.component.value.SELF_COMPUTED
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.tree.DefaultMutableTreeNode
import org.jdesktop.swingx.MultiSplitLayout

import org.jdesktop.swingx.JXMultiSplitPane
import org.jdesktop.swingx.MultiSplitLayout.*


class CeilConstraints(val modifies: List<(Table, Cell<*>) -> Unit>)

fun Modifier.toCeilConstraints(): CeilConstraints =
    getAllModifiers().map<Modification, (Table, Cell<*>) -> Unit> { mod ->
        when (mod) {
            is GravityModification         -> {
                { table, cell ->
                    when (mod.gravity) {
                        Gravity.TOP          -> cell.top()
                        Gravity.BOTTOM       -> cell.bottom()
                        Gravity.CENTER       -> cell.center()
                        Gravity.START        -> cell.left()
                        Gravity.END          -> cell.right()
                        Gravity.TOP_START    -> cell.top().left()
                        Gravity.TOP_END      -> cell.top().right()
                        Gravity.BOTTOM_START -> cell.bottom().left()
                        Gravity.BOTTOM_END   -> cell.bottom().right()
                    }
                }
            }

            is FillHeightModification      -> {
                { table, cell -> cell.expandY() }
            }
            is FillWidthModification       -> {
                { table, cell -> cell.expandX() }
            }

            is HeightModification          -> {
                { table, cell ->
                    when {
                        mod.height == MATCH_PARENT  -> cell.expandY()
                        mod.height == SELF_COMPUTED -> cell.fillY()
                        mod.height > 0              -> cell.height(mod.height.toFloat())
                    }
                }
            }
            is WidthModification           -> {
                { table, cell ->
                    when {
                        mod.width == MATCH_PARENT  -> cell.expandX()
                        mod.width == SELF_COMPUTED -> cell.fillX()
                        mod.width > 0              -> cell.width(mod.width.toFloat())
                    }
                }
            }

            is AddMarginBottomModification -> {
                { table, cell -> cell.padBottom(cell.padBottom + mod.margin) }
            }
            is AddMarginEndModification    -> {
                { table, cell -> cell.padRight(cell.padRight + mod.margin) }
            }
            is AddMarginStartModification  -> {
                { table, cell -> cell.padLeft(cell.padLeft + mod.margin) }
            }
            is AddMarginTopModification    -> {
                { table, cell -> cell.padTop(cell.padTop + mod.margin) }
            }

            is MarginBottomModification    -> {
                { table, cell -> cell.padBottom(cell.padBottom + mod.margin) }
            }
            is MarginEndModification       -> {
                { table, cell -> cell.padRight(cell.padRight + mod.margin) }
            }
            is MarginStartModification     -> {
                { table, cell -> cell.padLeft(cell.padLeft + mod.margin) }
            }
            is MarginTopModification       -> {
                { table, cell -> cell.padTop(cell.padTop + mod.margin) }
            }
            else                           -> {
                { _, _ -> println("NOTE: CellConstraints can't handle $mod") }
            }
        }
    }
        .let { CeilConstraints(it) }


abstract class TableComposer {

    data class View(val component: Component, val modifier: Modifier)

    protected val views = mutableListOf<View>()

    fun addComponent(component: Component, modifier: Modifier) {
        views.add(View(component, modifier))
    }

    fun <T : JComponent> T.addTo(composer: TableComposer, modifier: Modifier): T = apply { composer.addComponent(this, modifier) }

    protected fun modify(table: Table, cell: Cell<*>, modifier: Modifier) {
        val constraints = modifier.toCeilConstraints()
        for (modify in constraints.modifies) {
            modify(table, cell)
        }
    }
}

interface TableBuilder {

    fun build(): Table
}

class RowTableBuilder : TableComposer(), TableBuilder {

    override fun build(): Table {
        val table = Table()

        for ((component, modifier) in views) {
            val cell = table.addCell(component)
            modify(table, cell, modifier)
        }

        return table
    }
}

class ColumnTableBuilder : TableComposer(), TableBuilder {

    override fun build(): Table {
        val table = Table()

        for (i in views.indices) {
            val view = views[i]

            val cell = table.addCell(view.component)
            modify(table, cell, view.modifier)
            if (i < views.lastIndex) {
                table.row()
            }
        }

        return table
    }
}

fun panel(orientation: Orientation = Orientation.VERTICAL, compose: TableComposer.() -> Unit): Table {
    return ColumnTableBuilder().apply(compose).build()
}

fun TableComposer.column(modifier: Modifier = Modifier, compose: TableComposer.() -> Unit): Table =
    ColumnTableBuilder().apply(compose).build().addTo(this, modifier)

fun TableComposer.row(modifier: Modifier = Modifier, compose: TableComposer.() -> Unit): Table =
    RowTableBuilder().apply(compose).build().addTo(this, modifier)

fun TableComposer.label(modifier: Modifier = Modifier, text: String): XLabel = XLabel(text).also { label ->
    label.addTo(this, modifier)
}

fun TableComposer.button(modifier: Modifier = Modifier, text: String): JButton = JButton(text).also { label ->
    label.addTo(this, modifier)
}

fun main(args: Array<String>) {

    runTestFrame {
//        val table = Table()
//
//        table.addCell(XLabel("Head")).right().expandX()

        val column1 = Split()
        column1.setRowLayout(false)

        val row = Split()

        val column2 = Split()
        column2.setRowLayout(false)

        column2.setChildren(Leaf("middle.top"), Divider(), Leaf(
            "middle"), Divider(), Leaf("middle.bottom"))

        row.setChildren(Leaf("left").apply { weight = 0.5 }, Divider(), column2,
                        Divider(), Leaf("right").apply { weight = 0.5 })

        column1.setChildren(row, Divider(), Leaf("bottom"))

// Once the layout is done, the code is easy

// Once the layout is done, the code is easy
        val msp = JXMultiSplitPane()
        val layout = MultiSplitLayout(column1)
        msp.layout = layout
        msp.add(JPanel().apply { add(JButton("bottom")) }, "bottom")
        msp.add(JButton("left"), "left")
        msp.add(JButton("right"), "right")
        msp.add(JButton("middle.bottom"), "middle.bottom")
        msp.add(JButton("middle"), "middle")
        msp.add(JButton("middle.top"), "middle.top")
        msp

//        table.row()
//        table.stack(
//            Table().apply {
//                addCell(XLabel("ERROR")).expandX().right()
//                addCell(JButton("itoirt")).width(170f)
//                addCell(XLabel("sdasaasdasd"))
//            }
//        )
////        table.addCell(Stack().apply { Table().apply {
////            addCell(XLabel("ERROR")).expandX().right()
////            addCell(JButton("itoirt")).width(170f)
////            addCell(XLabel("sdasaasdasd"))
////        }.addTo(this, Modifier) })
//
////        table.addCell(XLabel("ERROR")).expandX().right()
////        table.addCell(JButton("itoirt")).width(170f)
////        table.addCell(XLabel("sdasaasdasd"))
//
//        table.row()
//        table.addCell(XLabel("ERROR"))
//        table.row()
//        table.addCell(XLabel("itoirt")).left()
//        table.row()
//        table.addCell(XLabel("sdasaasdasd")).right()
//
//        table.left().top().debug()
    }
}

//fun main() {
//    runTestFrame {
//        panel {
//            label(Modifier.fillWidth().gravity(Gravity.END), "HEAD")
//            row(Modifier.gravity(Gravity.CENTER).fillWidth()) {
//                label(Modifier.fillWidth().gravity(Gravity.END), text = "Pimpochka")
//                button(Modifier.width(170), text = "Clicker")
//                label(text = "Clicker")
//            }
//            column(Modifier.fillHeight()) {
//                label(text = "Clicker")
//                label(Modifier.gravity(Gravity.END), text = "Pimpochka")
//                label(text = "Clicker")
//                label(text = "Clicker")
//            }
//        }.debug().left().top()
//    }
//}


//        val button1 = JButton("One")
//        val button2 = JButton("Two")
//        val button3 = JButton("Three")
//        val button4 = JButton("Four")
//        val button5 = JButton("Five")
//        val text1 = JTextField("One")
//        val text2 = JTextField("Two")
//        val text3 = JTextField("Three")
//
//        val table = Table()
//
//        table.addCell(button1)
//        table.addCell(button2)
//        table.row()
//        table.addCell(button3).colspan(2)
//        table.row()
//        table.addCell(text1).colspan(2).fill()
//        table.row()
//        table.addCell(text2).right().width(150f)
//        table.addCell(text3).width(250f)
//        table.row()
//        table.addCell(button4).expand().colspan(2)
//        table.debug()
//
//        table