package dev.itssho.module.test.koin

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.koin.test.mock.MockProvider
import org.mockito.Mockito

class KoinMockExtension : BeforeEachCallback {

	override fun beforeEach(context: ExtensionContext?) {
		MockProvider.register { clazz ->
			Mockito.mock(clazz.java)
		}
	}
}