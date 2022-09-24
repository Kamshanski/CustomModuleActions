package dev.itssho.module.feature.qpay.module

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.DumbAware
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.qpay.module.actor.QpayModuleWizardActor
import dev.itssho.module.resources.R
import kotlinx.coroutines.runBlocking

// TODO это должно остаться в модуле App
class QpayModuleAction : AnAction(R.icon.qpay), DumbAware {

	override fun actionPerformed(e: AnActionEvent) {
        runBlocking {
			// null => IDE ещё не готова
            e.getData(CommonDataKeys.PROJECT) ?: return@runBlocking

            val context = JBContext.make(e.dataContext)
            QpayModuleWizardActor(context).runAction()
        }
	}
}