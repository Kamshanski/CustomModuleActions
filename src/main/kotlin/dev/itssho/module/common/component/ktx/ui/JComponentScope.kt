package dev.itssho.module.common.component.ktx.ui

import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing
import javax.swing.JComponent

fun makeSwingScope() = CoroutineScope(Job() + Dispatchers.Swing)

private const val SCOPE_PROPERTY = "dev.itssho.module.common.component.ktx.ui.scope"
var JComponent.scope: ScopeWrapper
    get() {
        val thisScope = getClientProperty(SCOPE_PROPERTY)
        if (thisScope is ScopeWrapper) {
            return thisScope
        }

        val parent = parent
        if (parent is JComponent) {
            return parent.scope
        }

        throw IllegalStateException("Component is scope unaware")
    }
    private set(value) {
        putClientProperty(SCOPE_PROPERTY, value)
    }