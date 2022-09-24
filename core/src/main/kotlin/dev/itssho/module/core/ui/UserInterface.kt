package dev.itssho.module.core.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.swing.Swing
import javax.swing.JComponent

abstract class UserInterface(
    // TODO вынести создание скоупа в контекст, пробрасывая его сюда. Чтобы убивать их всех после закрытия актора // context.coroutineScopeManager.makeNewScope()
    val scope: CoroutineScope,
) {

    abstract fun constructView(): JComponent

    open fun finish() {
        scope.cancel()
    }
}