package dev.itssho.module.common.component.mvvm.ui

import dev.itssho.module.common.component.ktx.scope.Scoped
import dev.itssho.module.common.component.value.Read
import dev.itssho.module.common.component.value.Var
import javax.swing.JComponent

interface MvvmComponent: Scoped {

    suspend fun <V, C : JComponent> C.watch(
        value: Read<V>,
        get: C.() -> V,
        set: C.(V) -> Unit,
    ): C = apply {
        value.observe { newValue ->
            val previousValue = get(this)
            if (previousValue != newValue) {
                set(newValue)
            }
        }
    }

    suspend fun <T, C : JComponent> C.bind(
        variable: Var<T>,
        get: C.() -> T,
        set: C.(T) -> Unit,
        installListener: C.(update: (T) -> Unit) -> Unit,
    ): C = apply {
        variable.observe { newValue ->
            val previousValue = get(this)
            if (previousValue != newValue) {
                set(newValue)
            }
        }

        installListener { variable.set(it) }
    }
}