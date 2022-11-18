package dev.itssho.module.listener.preload.script

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import dev.itssho.module.core.context.JBContext
import dev.itssho.module.listener.preload.script.di.PreloadScriptDi
import dev.itssho.module.service.action.module.domain.usecase.UpdateScriptsUseCase
import kotlinx.coroutines.runBlocking

class ProjectManagerListenerImpl : ProjectManagerListener {

	override fun projectOpened(project: Project) {
		val context = JBContext(project)
		val di = PreloadScriptDi(context)

		val updateScriptsUseCase = di.get<UpdateScriptsUseCase>()

		runBlocking {
			updateScriptsUseCase()
		}
	}
}