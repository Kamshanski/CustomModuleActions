package dev.itssho.module.util.koin

import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.new
import org.koin.dsl.ScopeDSL

inline fun <reified R> Module.factoryScopeOf(
	crossinline constructor: () -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }.also { scope<R>(scopeSet) }

inline fun <reified R, reified T1> Module.factoryScopeOf(
	crossinline constructor: (T1) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }.also { scope<R>(scopeSet) }

inline fun <reified R, reified T1, reified T2> Module.factoryScopeOf(
	crossinline constructor: (T1, T2) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }.also { scope<R>(scopeSet) }

inline fun <reified R, reified T1, reified T2, reified T3> Module.factoryScopeOf(
	crossinline constructor: (T1, T2, T3) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }.also { scope<R>(scopeSet) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4> Module.factoryScopeOf(
	crossinline constructor: (T1, T2, T3, T4) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }.also { scope<R>(scopeSet) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5> Module.factoryScopeOf(
	crossinline constructor: (T1, T2, T3, T4, T5) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }.also { scope<R>(scopeSet) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6> Module.factoryScopeOf(
	crossinline constructor: (T1, T2, T3, T4, T5, T6) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }.also { scope<R>(scopeSet) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7> Module.factoryScopeOf(
	crossinline constructor: (T1, T2, T3, T4, T5, T6, T7) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }.also { scope<R>(scopeSet) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8> Module.factoryScopeOf(
	crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }.also { scope<R>(scopeSet) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9> Module.factoryScopeOf(
	crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }.also { scope<R>(scopeSet) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10> Module.factoryScopeOf(
	crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }.also { scope<R>(scopeSet) }
