package dev.itssho.module.test.koin

import dev.itssho.module.util.koin.LocalKoinScope
import org.junit.jupiter.api.DynamicTest
import org.koin.core.annotation.KoinInternalApi

fun scopeDynamicTest(scopeHolder: LocalKoinScope): DynamicTest {
	val testName = scopeHolder::class.simpleName!!
	return DynamicTest.dynamicTest(testName) {
		assertScopedItemsAvailable(scopeHolder)
	}
}

@OptIn(KoinInternalApi::class)
private fun assertScopedItemsAvailable(scopeHolder: LocalKoinScope) {
	val scope = scopeHolder.scope
	val scopeValues = scope.getKoin().instanceRegistry.instances.filter { it.value.beanDefinition.scopeQualifier == scope.scopeQualifier }.values
	scopeValues.forEach {
		scope.get<Any>(it.beanDefinition.primaryType, it.beanDefinition.qualifier)
		it.beanDefinition.secondaryTypes.forEach { secondaryType -> scope.get<Any>(secondaryType, it.beanDefinition.qualifier) }
	}
}