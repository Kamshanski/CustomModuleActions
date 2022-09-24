package dev.itssho.module.uikit.modification

fun Modifier.marginTop(px: Int): Modifier = then(MarginTopModification(px))
fun Modifier.marginBottom(px: Int): Modifier = then(MarginBottomModification(px))
fun Modifier.marginStart(px: Int): Modifier = then(MarginStartModification(px))
fun Modifier.marginEnd(px: Int): Modifier = then(MarginEndModification(px))

fun Modifier.margin(px: Int): Modifier =
    marginTop(px).marginBottom(px).marginEnd(px).marginStart(px)

fun Modifier.marginVertical(px: Int): Modifier =
    marginTop(px).marginBottom(px)

fun Modifier.marginHorizontal(px: Int): Modifier =
    marginEnd(px).marginStart(px)


data class MarginTopModification(private val marginTop: Int) : Modification {

    override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder =
        constraints.also { it.marginTop = marginTop }
}

class MarginBottomModification(private val marginBottom: Int) : Modification {

    override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder =
        constraints.also { it.marginBottom = marginBottom }
}

class MarginStartModification(private val marginStart: Int) : Modification {

    override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder =
        constraints.also { it.marginStart = marginStart }
}

class MarginEndModification(private val marginEnd: Int) : Modification {

    override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder =
        constraints.also { it.marginEnd = marginEnd }
}

fun Modifier.addMarginTop(px: Int): Modifier = then(AddMarginTopModification(px))
fun Modifier.addMarginBottom(px: Int): Modifier = then(AddMarginBottomModification(px))
fun Modifier.addMarginStart(px: Int): Modifier = then(AddMarginStartModification(px))
fun Modifier.addMarginEnd(px: Int): Modifier = then(AddMarginEndModification(px))

fun Modifier.addMargin(px: Int): Modifier =
    addMarginTop(px).addMarginBottom(px).addMarginEnd(px).addMarginStart(px)

fun Modifier.addMarginVertical(px: Int): Modifier =
    addMarginTop(px).addMarginBottom(px)

fun Modifier.addMarginHorizontal(px: Int): Modifier =
    addMarginEnd(px).addMarginStart(px)

data class AddMarginTopModification(private val marginTop: Int) : Modification {

    override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder =
        constraints.also { it.marginTop = it.marginTop.coerceAtLeast(0) + marginTop }
}

class AddMarginBottomModification(private val marginBottom: Int) : Modification {

    override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder =
        constraints.also { it.marginBottom = it.marginBottom.coerceAtLeast(0) + marginBottom }
}

class AddMarginStartModification(private val marginStart: Int) : Modification {

    override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder =
        constraints.also { it.marginStart = it.marginStart.coerceAtLeast(0) + marginStart }
}

class AddMarginEndModification(private val marginEnd: Int) : Modification {

    override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder =
        constraints.also { it.marginEnd = it.marginEnd.coerceAtLeast(0) + marginEnd }
}