package di

import com.intellij.openapi.project.Project
import dev.itssho.module.core.context.JBContext
import dev.itssho.module.listener.preload.script.di.PreloadScriptDi
import dev.itssho.module.test.koin.KoinMockExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.check.checkModules
import org.mockito.kotlin.mock

@ExtendWith(KoinMockExtension::class)
class ScriptPreloadListenerDiTest {

	@Test
	fun `verify script preload listener di`() {
		val project: Project = mock()
		val context: JBContext = JBContext(project)

		val di = PreloadScriptDi(context)

		di.koin.checkModules()
	}
}