package dev.itssho.module.common.component.mvvm.ui

import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import dev.itssho.module.common.component.ktx.scope.Scoped
import dev.itssho.module.common.component.value.Observable
import dev.itssho.module.common.component.value.Observer
import dev.itssho.module.uikit.component.panel.JXLinearPanel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred

interface UserInterface: Scoped {

    val scope: ScopeWrapper

    override fun launch(block: suspend CoroutineScope.() -> Unit) {
        launch(block)
    }

    override fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> =
        scope.async(block)

    override fun <T> Observable<T>.observe(observer: Observer<T>) {
        observe(scope, observer)
    }

    fun constructView(basePanel: JXLinearPanel): JXLinearPanel
}