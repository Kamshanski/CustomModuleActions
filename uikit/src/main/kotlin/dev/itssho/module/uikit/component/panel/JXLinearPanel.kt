package dev.itssho.module.uikit.component.panel

import com.intellij.ui.components.panels.HorizontalLayout
import com.intellij.ui.components.panels.VerticalLayout
import dev.itssho.module.uikit.extensions.addOnContainerChangedListener
import dev.itssho.module.uikit.extensions.addOnResizedListener
import dev.itssho.module.uikit.extensions.addOnShownListener
import dev.itssho.module.uikit.layout.swan.linear.LinearLayout
import dev.itssho.module.uikit.layout.swan.linear.LinearOrientation
import dev.itssho.module.uikit.layout.v2.parameters.Orientation
import dev.itssho.module.uikit.util.plus
import java.awt.Component
import java.awt.Dimension
import java.awt.LayoutManager
import javax.swing.JComponent
import javax.swing.JPanel

// НИКОГДА не делать прозрачным фон, иначе будут баги с рисовкой компонентов
//        background = R.color.transparent
open class JXLinearPanel(orientation: Orientation, layoutProvider: LinearLayoutProvider = LinearLayoutProvider.SwanLayout) :
    JPanel(defineLayout(orientation, layoutProvider)) {

    init {
        addOnShownListener { updateUI() }
        addOnResizedListener { updateUI() }
        addOnContainerChangedListener { updateUI() }
    }

    override fun getMinimumSize(): Dimension {
        val insets = getInsets()
        return layout.minimumLayoutSize(this).plus(insets.left + insets.right, insets.top + insets.bottom)
    }

    @Deprecated("Use addComponent instead", ReplaceWith("addComponent(component)"))
    override fun add(comp: Component?): Component {
        check(comp is JComponent)
        addComponent(comp)
        return comp
    }

    @Deprecated("Use addComponent instead", ReplaceWith("addComponent(component)"))
    override fun add(comp: Component, constraints: Any?) {
        check(comp is JComponent)
        addComponent(comp)
    }

    @Deprecated("Use addComponent instead", ReplaceWith("addComponent(component, index)"))
    override fun add(comp: Component?, index: Int): Component {
        check(comp is JComponent)
        addComponent(comp, index)
        return comp
    }

    @Deprecated("Use addComponent instead", ReplaceWith("addComponent(component)"))
    override fun add(name: String?, comp: Component?): Component {
        check(comp is JComponent)
        addComponent(comp)
        return comp
    }

    @Deprecated("Use addComponent instead", ReplaceWith("addComponent(component, index)"))
    override fun add(comp: Component?, constraints: Any?, index: Int) {
        check(comp is JComponent)
        addComponent(comp, index)
    }

    fun addComponent(component: JComponent, index: Int = -1) {
        super.add(component, -1)
    }

    companion object {

        private fun defineLayout(orientation: Orientation, layoutProvider: LinearLayoutProvider): LayoutManager = when (layoutProvider) {
            LinearLayoutProvider.JetBrains  -> selectJetBrainsLinearLayout(orientation)
            LinearLayoutProvider.SwanLayout -> selectSwanLinearLayout(orientation)
            LinearLayoutProvider.GridBag    -> selectGridBagLinearLayout(orientation)
        }

        // Не поддерживает margin и weight. Можно заморочиться и поддержать хотябы margin, но можно и нет
        private fun selectJetBrainsLinearLayout(orientation: Orientation) = when (orientation) {
            Orientation.HORIZONTAL -> HorizontalLayout(0)
            Orientation.VERTICAL   -> VerticalLayout(0)
        }

        private fun selectSwanLinearLayout(orientation: Orientation) = when (orientation) {
            Orientation.HORIZONTAL -> LinearLayout(LinearOrientation.Horizontal)
            Orientation.VERTICAL   -> LinearLayout(LinearOrientation.Vertical)
        }

        private fun selectGridBagLinearLayout(orientation: Orientation) = when (orientation) {
            Orientation.HORIZONTAL -> LinearLayout(LinearOrientation.Horizontal)
            Orientation.VERTICAL   -> LinearLayout(LinearOrientation.Vertical)
        }
    }
}