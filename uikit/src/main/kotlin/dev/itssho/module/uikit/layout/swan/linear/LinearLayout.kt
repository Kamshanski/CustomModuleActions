package dev.itssho.module.uikit.layout.swan.linear

import dev.itssho.module.uikit.layout.swan.SWAN_MATCH_PARENT
import dev.itssho.module.uikit.layout.swan.SWAN_WRAP_CONTENT
import dev.itssho.module.uikit.layout.swan.frame.FrameLayout
import dev.itssho.module.uikit.util.preferredOrMinSize
import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import java.awt.LayoutManager2
import javax.swing.JComponent
import javax.swing.JPanel

class WeightCalc(
    private val components: Map<JComponent, LinearConstraints>,
) {

    fun weightOf(component: LinearConstraints): Double {
        if (component.weight <= 0) {
            return 0.0
        }

        val sum = components.values.sumBy { it.weight }
        val weight = component.weight.toDouble()
        return weight / sum
    }
}

class LinearLayout(private val orientation: LinearOrientation) : LayoutManager2 {

    private val components: MutableMap<JComponent, LinearConstraints> = mutableMapOf()
    private val weightCalc = WeightCalc(components)
    private var maxWidth: Int = 0
    private var maxHeight: Int = 0
    private var wrapWidth: Int = 0
    private var wrapHeight: Int = 0
    private var remainWidth: Int = 0
    private var remainHeight: Int = 0
    private var x: Int = 0
    private var y: Int = 0

    fun addLayoutComponent(comp: JComponent) {
        addLayoutComponent(comp, comp.linearConstraints)
    }

    @Deprecated("Do not use it directly. Use [addLayoutComponent(comp: JComponent)] instead")
    override fun addLayoutComponent(comp: Component?, constraints: Any?) {
        if (comp == null || comp !is JComponent) {
            return
        }

        val innerConstraints = comp.linearConstraints

        components[comp] = when (constraints) {
            is LinearConstraints -> constraints.copy()
            else                 -> innerConstraints.copy()
        }
    }

    @Deprecated("Do not use it directly. Use [addLayoutComponent(comp: JComponent)] instead")
    override fun addLayoutComponent(name: String, comp: Component) {
        throw NotImplementedError("Not implemented here. Use another addLayoutComponent(comp: Component?, constraints: Any?)")
    }

    override fun removeLayoutComponent(comp: Component) {
        components.remove(comp)
    }

    override fun preferredLayoutSize(parent: Container): Dimension {
        return Dimension(parent.width, parent.height)
    }

    override fun minimumLayoutSize(parent: Container): Dimension {
        val width = when (orientation) {
            LinearOrientation.Vertical   -> components.map { (view, constraints) -> view.bounds.width + constraints.marginEnd + constraints.marginStart }.maxBy { it }
            LinearOrientation.Horizontal -> components.entries.sumBy { (view, constraints) -> view.bounds.width + constraints.marginEnd + constraints.marginStart }
        } ?: parent.width
        val height = when (orientation) {
            LinearOrientation.Vertical   -> components.entries.sumBy {  (view, constraints) -> view.bounds.height + constraints.marginTop + constraints.marginBottom }
            LinearOrientation.Horizontal -> components.map {  (view, constraints) -> view.bounds.height + constraints.marginTop + constraints.marginBottom }.maxBy { it }
        } ?: parent.height
        return Dimension(width, height)
    }

    override fun maximumLayoutSize(parent: Container): Dimension {
        return Dimension(parent.width, parent.height)
    }

    override fun layoutContainer(parent: Container) {
        // Values
        val insets = parent.insets
        maxWidth = parent.width - (insets.left + insets.right)
        maxHeight = parent.height - (insets.top + insets.bottom)
        remainWidth = maxWidth
        remainHeight = maxHeight
        x = 0
        y = insets.top

        // Do layout for LinearLayout & FrameLayout
        //  AND
        // Calculate weights
        components.forEach { (component, _) ->
            if (component is JPanel) {
                doChildLayout(component)
            }
        }

        // Components container
        val weightComponents: MutableMap<JComponent, LinearConstraints> = mutableMapOf()
        val matchComponents: MutableMap<JComponent, LinearConstraints> = mutableMapOf()
        val normalComponents: MutableMap<JComponent, LinearConstraints> = mutableMapOf()

        // Calculate component's size
        when (orientation) {
            LinearOrientation.Vertical   -> {
                // Group components
                components.forEach { i ->
                    val c = i.value
                    when {
                        (c.getCalculatedWeight() > 0 && c.height == 0) -> weightComponents[i.key] = i.value
                        (c.height == SWAN_MATCH_PARENT)                -> matchComponents[i.key] = i.value
                        else                                           -> normalComponents[i.key] = i.value
                    }
                }
                if (weightComponents.isNotEmpty()) {
                    matchComponents.forEach { i ->
                        i.value.height = SWAN_WRAP_CONTENT // Override match parent if contain weight
                    }
                }

                // Calculate size
                calculateVerticalComponentSize(normalComponents)
                calculateVerticalComponentSize(matchComponents)
                calculateVerticalComponentSize(weightComponents, true)

                // Set bounds
                setVerticalComponentBounds()
            }
            LinearOrientation.Horizontal -> {
                // Group components
                components.forEach { i ->
                    val c = i.value
                    when {
                        (c.getCalculatedWeight() > 0 && c.width == 0) -> weightComponents[i.key] = i.value
                        (c.width == SWAN_MATCH_PARENT)                -> matchComponents[i.key] = i.value
                        else                                          -> normalComponents[i.key] = i.value
                    }
                }
                if (weightComponents.isNotEmpty()) {
                    matchComponents.forEach { i ->
                        i.value.width = SWAN_WRAP_CONTENT // Override match parent if contain weight
                    }
                }

                // Calculate size
                calculateHorizontalComponentSize(normalComponents)
                calculateHorizontalComponentSize(matchComponents)
                calculateHorizontalComponentSize(weightComponents, true)

                // Set bounds
                setHorizontalComponentBounds()
            }
        }

        // Set wrap size
        if (maxWidth <= 0) {
            parent.size = Dimension(wrapWidth, parent.height)
            parent.preferredSize = parent.size
        }
        if (maxHeight <= 0) {
            parent.size = Dimension(parent.width, wrapHeight)
            parent.preferredSize = parent.size
        }
    }

    // Do layout for LinearLayout & FrameLayout
    private fun doChildLayout(panel: JPanel) {
        val layout = panel.layout
        if (layout is LinearLayout || layout is FrameLayout) {
            panel.doLayout()
        }
    }

    fun LinearConstraints.getCalculatedWeight(): Double =
        weightCalc.weightOf(this)

    private fun calculateVerticalComponentSize(
        comps: MutableMap<JComponent, LinearConstraints>,
        isWeight: Boolean = false,
    ) {
        comps.forEach { i ->
            // Value
            val component = i.key
            val constraints = i.value

            // Check visibility
            if (component.isVisible) {
                // Calculate width
                val width = when {
                    constraints.width > 0                  -> constraints.width
                    constraints.width == SWAN_MATCH_PARENT -> {
                        val w = maxWidth - (constraints.marginStart + constraints.marginEnd)
                        if (w >= component.preferredOrMinSize.width) {
                            w
                        } else {
                            component.preferredOrMinSize.width
                        }
                    }
                    else                                   -> component.preferredOrMinSize.width
                }

                // Calculate height
                val height = when {
                    constraints.height > 0                                           -> constraints.height
                    constraints.height == SWAN_MATCH_PARENT                          -> {
                        val h = remainHeight - (constraints.marginTop + constraints.marginBottom)
                        if (h >= component.preferredOrMinSize.height) {
                            h
                        } else {
                            component.preferredOrMinSize.height
                        }
                    }
                    constraints.height == 0 && constraints.getCalculatedWeight() > 0 -> {
                        val h =
                            (remainHeight * constraints.getCalculatedWeight()).toInt() - (constraints.marginTop + constraints.marginBottom)
                        if (h >= component.preferredOrMinSize.height) {
                            h
                        } else {
                            component.preferredOrMinSize.height
                        }
                    }
                    else                                                             -> component.preferredOrMinSize.height
                }

                // Set wrap size
                val marginWidth = (width + constraints.marginStart + constraints.marginEnd)
                val marginHeight = (height + constraints.marginTop + constraints.marginBottom)
                if (marginWidth > wrapWidth) {
                    wrapWidth = marginWidth
                }
                wrapHeight += marginHeight

                // Set Size
                constraints.calculatedWidth = width
                constraints.calculatedHeight = height
                components[component] = constraints

                // Calculate remain height
                if (!isWeight) {
                    remainHeight -= marginHeight
                }
            }
        }
    }

    private fun calculateHorizontalComponentSize(
        comps: MutableMap<JComponent, LinearConstraints>,
        isWeight: Boolean = false,
    ) {
        comps.forEach { i ->
            // Value
            val component = i.key
            val constraints = i.value

            // Check visibility
            if (component.isVisible) {
                // Calculate width
                val width = when {
                    constraints.width > 0                                           -> constraints.width
                    constraints.width == SWAN_MATCH_PARENT                          -> {
                        val h = remainWidth - (constraints.marginStart + constraints.marginEnd)
                        if (h >= component.preferredOrMinSize.width) {
                            h
                        } else {
                            component.preferredOrMinSize.width
                        }
                    }
                    constraints.width == 0 && constraints.getCalculatedWeight() > 0 -> {
                        val h =
                            (remainWidth * constraints.getCalculatedWeight()).toInt() - (constraints.marginStart + constraints.marginEnd)
                        if (h >= component.preferredOrMinSize.width) {
                            h
                        } else {
                            component.preferredOrMinSize.width
                        }
                    }
                    else                                                            -> component.preferredOrMinSize.width
                }

                // Calculate width
                val height = when {
                    constraints.height > 0                  -> constraints.height
                    constraints.height == SWAN_MATCH_PARENT -> {
                        val w = maxHeight - (constraints.marginTop + constraints.marginBottom)
                        if (w >= component.preferredOrMinSize.height) {
                            w
                        } else {
                            component.preferredOrMinSize.height
                        }
                    }
                    else                                    -> component.preferredOrMinSize.height
                }

                // Set wrap size
                val marginWidth = (width + constraints.marginStart + constraints.marginEnd)
                val marginHeight = (height + constraints.marginTop + constraints.marginBottom)
                wrapWidth += marginWidth
                if (marginHeight > wrapHeight) {
                    wrapHeight = marginHeight
                }

                // Set Size
                constraints.calculatedWidth = width
                constraints.calculatedHeight = height
                components[component] = constraints

                // Calculate remain width
                if (!isWeight) {
                    remainWidth -= marginWidth
                }
            }
        }
    }

    private fun setVerticalComponentBounds() {
        components.forEach { i ->
            // Value
            val component = i.key
            val constraints = i.value

            // Check visibility
            if (component.isVisible) {
                // Size
                val width = constraints.calculatedWidth
                val height = constraints.calculatedHeight

                // Calculate position
                y += constraints.marginTop
                x = if (constraints.width != SWAN_MATCH_PARENT) {
                    when (constraints.gravity) {
                        LinearGravity.START  -> constraints.marginStart
                        LinearGravity.CENTER -> {
                            (maxWidth / 2) - (width / 2)
                        }
                        LinearGravity.END    -> {
                            maxWidth - (width + constraints.marginEnd)
                        }
                        else                 -> constraints.marginStart
                    }
                } else {
                    constraints.marginStart
                }

                // Set bounds
                component.setBounds(x, y, width, height)

                // Add margin bottom
                y += height + constraints.marginBottom
            }
        }
    }

    private fun setHorizontalComponentBounds() {
        components.forEach { i ->
            // Value
            val component = i.key
            val constraints = i.value

            // Check visibility
            if (component.isVisible) {
                // Size
                val width = constraints.calculatedWidth
                val height = constraints.calculatedHeight

                // Calculate position
                x += constraints.marginStart
                y = if (constraints.height != SWAN_MATCH_PARENT) {
                    when (constraints.gravity) {
                        LinearGravity.TOP    -> constraints.marginTop
                        LinearGravity.CENTER -> {
                            (maxHeight / 2) - (height / 2)
                        }
                        LinearGravity.BOTTOM -> {
                            maxHeight - (height + constraints.marginBottom)
                        }
                        else                 -> constraints.marginTop
                    }
                } else {
                    constraints.marginTop
                }

                // Set bounds
                component.setBounds(x, y, width, height)

                // Add margin bottom
                x += width + constraints.marginEnd
            }
        }
    }

    override fun getLayoutAlignmentX(target: Container?): Float {
        return JComponent.CENTER_ALIGNMENT
    }

    override fun getLayoutAlignmentY(target: Container?): Float {
        return JComponent.CENTER_ALIGNMENT
    }

    override fun invalidateLayout(target: Container?) {}
}
