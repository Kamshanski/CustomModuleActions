package dev.itssho.module.service.action.module

import com.intellij.openapi.components.service
import dev.itssho.module.core.context.JBContext

class ModuleActionServiceFactory(private val context: JBContext) {

	fun get(): ModuleActionService
		= context.ideProject.service()
}