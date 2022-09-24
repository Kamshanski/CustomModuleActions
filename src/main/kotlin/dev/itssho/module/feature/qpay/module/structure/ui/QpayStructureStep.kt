package dev.itssho.module.feature.qpay.module.structure.ui

import dev.itssho.module.common.component.action.ui.Context
import dev.itssho.module.common.component.action.ui.IdeaContext
import dev.itssho.module.common.component.action.ui.SwingContext
import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import dev.itssho.module.feature.qpay.module.name.ui.QpayModuleCreationResult
import dev.itssho.module.resources.Strings
import dev.itssho.module.uikit.hosts.dialog.idea.IdeaYesNoDialogConstructor
import dev.itssho.module.uikit.hosts.dialog.swing.SwingYesNoDialogConstructor

@Suppress("FunctionName")
fun QpayStructureStep(context: Context): QpayModuleCreationResult {
    val scope = ScopeWrapper.newMain()

    val ui = QpayStructureUi(scope)

    when (context) {
        is IdeaContext  -> {
            IdeaYesNoDialogConstructor(Strings.Structure.title, context.ideProject) {
                ui.constructView(this)
            }.build().apply {
//            val okBtn = getOkButton()
//            okBtn?.watch(viewModel.canCreateModule, get = { isEnabled }, set = { invokeLater(project) { isEnabled = it } })
            }.showUp()
        }
        is SwingContext -> {
            SwingYesNoDialogConstructor(Strings.Structure.title, context.frame, ) {
                ui.constructView(this)
            }.build().apply {
//            val okBtn = getOkButton()
//            okBtn?.watch(viewModel.canCreateModule, get = { isEnabled }, set = { invokeLater(project) { isEnabled = it } })
            }.showUp()
        }
    }

    val result = QpayModuleCreationResult.Nothing()
//    val result = ui.delegate
//        .viewState.get().fullModuleName
//        .splitToChain(Separator.Minus)
//        .let { QpayModuleCreationResult.Module(it) }

    return result
}