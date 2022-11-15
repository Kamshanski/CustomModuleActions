package dev.itssho.module.util.koin

import org.koin.core.Koin
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.dsl.ScopeDSL

/*
	Parametrized означает, что параметры обязательно будут.
	LocalKoin означает, что первый аргумент это koin: Koin и что конструктов возвращает наследника LocalKoinScope
	Поэтому для конструкторов (Koin) -> R и () -> R должны использоваться прочие функции. Например factoryScopeOf
*/

inline fun <reified R : LocalKoinScope, reified T2> Module.parametrizedLocalKoinScopeFactoryOf(
	crossinline constructor: (Koin, T2) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = 
	factory { constructor(get(), it.get()) }
		.also { scope<R>(scopeSet) }

inline fun <reified R : LocalKoinScope, reified T2, reified T3> Module.parametrizedLocalKoinScopeFactoryOf(
	crossinline constructor: (Koin, T2, T3) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = 
	factory { constructor(get(), it.get(), it.get()) }
		.also { scope<R>(scopeSet) }

inline fun <reified R : LocalKoinScope, reified T2, reified T3, reified T4> Module.parametrizedLocalKoinScopeFactoryOf(
	crossinline constructor: (Koin, T2, T3, T4) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = 
	factory { constructor(get(), it.get(), it.get(), it.get()) }
		.also { scope<R>(scopeSet) }

inline fun <reified R : LocalKoinScope, reified T2, reified T3, reified T4, reified T5> Module.parametrizedLocalKoinScopeFactoryOf(
	crossinline constructor: (Koin, T2, T3, T4, T5) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = 
	factory { constructor(get(), it.get(), it.get(), it.get(), it.get()) }
		.also { scope<R>(scopeSet) }

inline fun <reified R : LocalKoinScope, reified T2, reified T3, reified T4, reified T5, reified T6> Module.parametrizedLocalKoinScopeFactoryOf(
	crossinline constructor: (Koin, T2, T3, T4, T5, T6) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = 
	factory { constructor(get(), it.get(), it.get(), it.get(), it.get(), it.get()) }
		.also { scope<R>(scopeSet) }

inline fun <reified R : LocalKoinScope, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7> Module.parametrizedLocalKoinScopeFactoryOf(
	crossinline constructor: (Koin, T2, T3, T4, T5, T6, T7) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = 
	factory { constructor(get(), it.get(), it.get(), it.get(), it.get(), it.get(), it.get()) }
		.also { scope<R>(scopeSet) }

inline fun <reified R : LocalKoinScope, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8> Module.parametrizedLocalKoinScopeFactoryOf(
	crossinline constructor: (Koin, T2, T3, T4, T5, T6, T7, T8) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = 
	factory { constructor(get(), it.get(), it.get(), it.get(), it.get(), it.get(), it.get(), it.get()) }
		.also { scope<R>(scopeSet) }

inline fun <reified R : LocalKoinScope, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9> Module.parametrizedLocalKoinScopeFactoryOf(
	crossinline constructor: (Koin, T2, T3, T4, T5, T6, T7, T8, T9) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = 
	factory { constructor(get(), it.get(), it.get(), it.get(), it.get(), it.get(), it.get(), it.get(), it.get()) }
		.also { scope<R>(scopeSet) }

inline fun <reified R : LocalKoinScope, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified Koin0> Module.parametrizedLocalKoinScopeFactoryOf(
	crossinline constructor: (Koin, T2, T3, T4, T5, T6, T7, T8, T9, Koin0) -> R,
	crossinline scopeSet: ScopeDSL.() -> Unit,
): Pair<Module, InstanceFactory<R>> = 
	factory { constructor(get(), it.get(), it.get(), it.get(), it.get(), it.get(), it.get(), it.get(), it.get(), it.get()) }
		.also { scope<R>(scopeSet) }
