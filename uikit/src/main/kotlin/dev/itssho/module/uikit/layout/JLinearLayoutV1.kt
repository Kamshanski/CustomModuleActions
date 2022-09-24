//package dev.itssho.module.uikit.layout
//
//import dev.itssho.module.common.component.swing.component.JXSpace
//import dev.itssho.module.uikit.layout.swan.linear.weight
//import dev.itssho.module.common.component.swing.parameters.Gravity
//import dev.itssho.module.common.component.swing.parameters.Orientation
//import dev.itssho.module.uikit.layout.v2.parameters.Gravity
//import dev.itssho.module.uikit.layout.v2.parameters.Orientation
//import java.awt.Color
//import java.awt.Component
//import java.awt.GridBagConstraints
//import java.awt.GridBagLayout
//import javax.swing.JComponent
//import javax.swing.JPanel
//
//open class JLinearLayoutV1(private val orientation: Orientation = Orientation.VERTICAL, val gravity: Gravity = Gravity.START) : JComponent() {
//
//    private val basePanel = JPanel().apply {
//        background = Color(0,0,0,0)
//        isOpaque = true
//    }
//
//    private val childrenList: MutableList<ComponentHolder> = mutableListOf()
//
//    private class ComponentHolder(
//        var component: Component,
//        var weight: Double = 0.0,
//    )
//
//    fun add(component: JComponent): JLinearLayoutV1 = apply {
//        val componentHolder = ComponentHolder(component, component.weight)
//        childrenList.add(componentHolder)
//    }
//
//    fun toPanel(): JPanel = basePanel.also { panel ->
//        // Добавил пустую панель справа или снизу, чтобы она подпирала элементы клеву или кверху
//        val childrenWithSpacer = addGravitySpacer(childrenList)
//        val countOfChildObject = childrenWithSpacer.size
//
//        val layout = GridBagLayout()
//        panel.layout = layout
//
//        for (i in 0 until countOfChildObject) {
//            val childComponentHolder = childrenWithSpacer[i]
//            val childComponent = childComponentHolder.component
//            val childComponentWeight = childComponentHolder.weight
//
//            val gbc = GridBagConstraints()
//            if (orientation == Orientation.VERTICAL) {
//                gbc.gridx = 0
//                gbc.gridy = i
//                gbc.weightx = 1.0
//                gbc.weighty = childComponentWeight
//            } else {
//                gbc.gridx = i
//                gbc.gridy = 0
//                gbc.weightx = childComponentWeight
//                gbc.weighty = 1.0
//            }
//            gbc.gridwidth = 1
//            gbc.gridheight = 1
//
//            gbc.boost()
//            gbc.orient()
//            if (childComponent is JLinearLayoutV1) {
//                gbc.orient()
//                layout.setConstraints(childComponent.basePanel, gbc)
//                val childPanel = childComponent.toPanel()
//                panel.add(childPanel)
//            } else {
//                layout.setConstraints(childComponent, gbc)
//                panel.add(childComponent)
//            }
//        }
//    }
//
//    private fun addGravitySpacer(original: List<ComponentHolder>): List<ComponentHolder> {
//        val componentsHasWeights = original.any { it.weight > 0.0 }
//        if (componentsHasWeights) {
//            return original
//        }
//
//        return when (gravity) {
//            Gravity.START  -> original + listOf(ComponentHolder(JXSpace(), 1.0))
//            Gravity.END    -> listOf(ComponentHolder(JXSpace(), 1.0)) + original
//            Gravity.CENTER -> original
//        }
//    }
//
//
//    private fun GridBagConstraints.boost() {
//        fill = if (orientation == Orientation.VERTICAL) {
//            GridBagConstraints.HORIZONTAL
//        } else {
//            GridBagConstraints.VERTICAL
//        }
//    }
//
//    private fun GridBagConstraints.orient() {
//        anchor = if (orientation == Orientation.VERTICAL) {
//            GridBagConstraints.PAGE_START
//        } else {
//            GridBagConstraints.LINE_START
//        }
//    }
//
//
//    /* Всякие делегаты на реальную панель */
//
//    override fun setEnabled(isEnabled: Boolean) {
//        super.setEnabled(isEnabled)
//        basePanel.isEnabled = isEnabled
//    }
//
//    override fun setBackground(color: Color) {
//        super.setBackground(color)
//        basePanel.background = color
//    }
//
//    override fun setVisible(isVisible: Boolean) {
//        super.setVisible(isVisible)
//        basePanel.isVisible = isVisible
//    }
//}