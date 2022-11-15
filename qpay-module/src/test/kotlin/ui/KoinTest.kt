package ui

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.core.scope.Scope
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.check.checkModules
import org.koin.test.junit5.mock.MockProviderExtension
import org.mockito.Mockito

private data class ObjQualifier(private val any: Any) : Qualifier {
	override val value: QualifierValue = "Str_$any"
}
private data class IntQualifier(private val any: Any) : Qualifier {
	override val value: QualifierValue = "Int_$any"
}

class Container: KoinScopeComponent {
	override val scope: Scope by lazy { createScope(this) }
}

interface I

class C: I

data class Obj(val double: I)

class KoinTest {

	@RegisterExtension
	val mockProvider = MockProviderExtension.create { clazz ->
		// Setup your nock framework
		Mockito.mock(clazz.java)
	}

	@Test
	fun koinTestwww() {
		val koinApp = koinApplication()

		val mCommon = module {
			single { 2.6 }
		}
		val m1 = module {
			factory { Container() }
			scope<Container> {
				factory { parametersHolder: ParametersHolder ->
					Obj(parametersHolder.get())
				}
			}
			factory { parametersHolder ->
				parametersHolder.get<I>().toString()
			}
		}

		koinApp.modules(mCommon, m1)

		koinApp.checkModules {
			withParameters<String> {
				ParametersHolder(mutableListOf(C()))
			}
			withParameters<Obj> {
				ParametersHolder(mutableListOf(C() as I))
			}
		}
	}
}