package dev.itssho.module.common.component.value

import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import kotlinx.coroutines.CoroutineScope

open class Var<T>(value: T, scope: ScopeWrapper) : Val<T>(value, scope), Write<T> {
    constructor(value: T, scope: CoroutineScope): this(value, ScopeWrapper(scope))

    override fun set(value: T) {
        flow.value = value
    }
}