package action.test

import common.runTestFrame
import dev.itssho.module.core.actor.Context
import dev.itssho.module.qpay.module.actor.QpayModuleWizardActor

suspend fun main() {
    runTestFrame {
        val context: Context = SwingContext(this)
        QpayModuleWizardActor(context).runAction()
    }
}