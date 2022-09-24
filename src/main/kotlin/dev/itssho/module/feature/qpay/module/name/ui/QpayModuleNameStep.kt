package dev.itssho.module.feature.qpay.module.name.ui

import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.action.ui.Context
import dev.itssho.module.common.component.action.ui.IdeaContext
import dev.itssho.module.common.component.action.ui.SwingContext
import dev.itssho.module.common.component.chain.splitToChain
import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import dev.itssho.module.resources.Strings
import dev.itssho.module.uikit.hosts.dialog.idea.IdeaYesNoDialogConstructor
import dev.itssho.module.uikit.hosts.dialog.swing.SwingYesNoDialogConstructor

fun QpayModuleNameStep(context: Context): QpayModuleCreationResult {

    val title = Strings.Name.title

    val scope = ScopeWrapper.newMain()

    val moduleCreationUi = QpayModuleNameUi(scope)

    when (context) {
        is IdeaContext -> {
            IdeaYesNoDialogConstructor(title, context.ideProject) {
                moduleCreationUi.constructView(this)
            }.build().apply {
//            val okBtn = getOkButton()
//            okBtn?.watch(viewModel.canCreateModule, get = { isEnabled }, set = { invokeLater(project) { isEnabled = it } })
            }.showUp()
        }
        is SwingContext -> {
            SwingYesNoDialogConstructor(title, context.frame, ) {
                moduleCreationUi.constructView(this)
            }.build().apply {
//            val okBtn = getOkButton()
//            okBtn?.watch(viewModel.canCreateModule, get = { isEnabled }, set = { invokeLater(project) { isEnabled = it } })
            }.showUp()
        }
    }

    val result = moduleCreationUi.delegate
        .viewState.get().fullModuleName
        .splitToChain(Separator.Minus)
        .let { QpayModuleCreationResult.Module(it) }

    return result
}