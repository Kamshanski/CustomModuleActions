package dev.itssho.module.util.koin

import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.new
import org.koin.dsl.ScopeDSL

inline fun <reified R> ScopeDSL.factoryOf(
	crossinline constructor: () -> R,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }

inline fun <reified R, reified T1> ScopeDSL.factoryOf(
	crossinline constructor: (T1) -> R,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }

inline fun <reified R, reified T1, reified T2> ScopeDSL.factoryOf(
	crossinline constructor: (T1, T2) -> R,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3> ScopeDSL.factoryOf(
	crossinline constructor: (T1, T2, T3) -> R,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4> ScopeDSL.factoryOf(
	crossinline constructor: (T1, T2, T3, T4) -> R,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5> ScopeDSL.factoryOf(
	crossinline constructor: (T1, T2, T3, T4, T5) -> R,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6> ScopeDSL.factoryOf(
	crossinline constructor: (T1, T2, T3, T4, T5, T6) -> R,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7> ScopeDSL.factoryOf(
	crossinline constructor: (T1, T2, T3, T4, T5, T6, T7) -> R,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8> ScopeDSL.factoryOf(
	crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8) -> R,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9> ScopeDSL.factoryOf(
	crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10> ScopeDSL.factoryOf(
	crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R,
): Pair<Module, InstanceFactory<R>> = factory { new(constructor) }
