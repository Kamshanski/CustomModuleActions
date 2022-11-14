package dev.itssho.module.feature.qpay.module

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import coroutine.gather
import dev.itssho.module.resources.R
import dev.itssho.module.service.action.module.ActionItem
import dev.itssho.module.service.action.module.ModuleActionService
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.nio.file.Path

class TestModuleAction : AnAction(R.icon.test), DumbAware {

	val scope = MainScope()

	override fun actionPerformed(e: AnActionEvent) {
		val project = e.project!!
		val service = ModuleActionService.getInstance(project)

		scope.launch {
			var i = 0
			service.state.onCompletion { println("colletcion completed") }.gather { syncMap: Map<Path, ActionItem> ->
				val entries = syncMap.toMutableMap().values
				val mes = StringBuilder("\nIteration ${i++}:\n\t").apply {
					entries.joinTo(this, separator = "\t\n") { it.toString() }
					append("\n")
				}.toString()

				println(mes)
			}
		}
			.invokeOnCompletion {
				println("flow coroutine completed")
			}
		scope.launch {
			service.loadActions(listOf(Path.of("C:\\Users\\ruban\\IdeaProjects\\CustomModuleActions\\qpay-module\\src\\test\\kotlin\\script")))
		}.invokeOnCompletion {
			println("load actoins completed")
		}
	}
}