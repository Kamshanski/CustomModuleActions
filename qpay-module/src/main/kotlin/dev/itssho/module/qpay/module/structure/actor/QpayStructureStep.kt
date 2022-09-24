package dev.itssho.module.qpay.module.structure.actor

import dev.itssho.module.component.resources.Strings
import dev.itssho.module.core.actor.Context
import dev.itssho.module.qpay.module.structure.actor.di.QpayStructureDi
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureNavAction
import dev.itssho.module.shared.dialog.ui.YesNoPlatformDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun QpayStructureStep(context: Context, di: QpayStructureDi): QpayStructureStepResult {
	val navScope = di.getNavigationScope()
	val viewModel = di.getViewModel()
	val ui = di.getUi()

	val result = suspendCoroutine<QpayStructureStepResult> { continuation ->
		YesNoPlatformDialog(
			title = Strings.Structure.title,
			context = context,
			okListener = { viewModel.proceed() },
			cancelListener = { viewModel.close() },
			windowCloseListener = { viewModel.close() },
		) {
			navScope.launch {
				viewModel.navAction.collect { action ->
					when (action) {
						is QpayStructureNavAction.Continue -> {
							continuation.resume(QpayStructureStepResult.Structure(action.structure))
						}
						is QpayStructureNavAction.Close    -> {
							continuation.resume(QpayStructureStepResult.Nothing)
						}
						else                               -> {}
					}
				}
			}
			ui.constructView()
		}
	}

	ui.finish()
	viewModel.finish()

	return result
}