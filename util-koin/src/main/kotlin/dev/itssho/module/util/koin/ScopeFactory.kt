package dev.itssho.module.util.koin

import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersHolder
import org.koin.core.scope.Scope
import org.koin.dsl.ScopeDSL

inline fun <reified R> Module.scopeFactory(
	crossinline constructor: Scope.(params: ParametersHolder) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = factory { constructor(it) }.also { scope<R>(scopeSet) }
