package dev.itssho.module.common.component.mvvm.presentation

import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import dev.itssho.module.common.component.ktx.scope.Scoped
import dev.itssho.module.common.component.value.Val
import dev.itssho.module.common.component.value.Var

abstract class ViewModel(val scope: ScopeWrapper) : Scoped by scope {

    fun <T> varOf(value: T): Var<T> = Var(value, scope)

    fun <T> valOf(value: T): Val<T> = Val(value, scope)
}