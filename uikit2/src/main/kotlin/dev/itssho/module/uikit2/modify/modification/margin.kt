package dev.itssho.module.uikit2.modify.modification

import dev.itssho.module.uikit2.modify.Modifier

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


data class MarginTopModification(val margin: Int) : Modification

class MarginBottomModification(val margin: Int) : Modification

class MarginStartModification(val margin: Int) : Modification

class MarginEndModification(val margin: Int) : Modification

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

data class AddMarginTopModification(val margin: Int) : Modification

class AddMarginBottomModification(val margin: Int) : Modification

class AddMarginStartModification(val margin: Int) : Modification

class AddMarginEndModification(val margin: Int) : Modification