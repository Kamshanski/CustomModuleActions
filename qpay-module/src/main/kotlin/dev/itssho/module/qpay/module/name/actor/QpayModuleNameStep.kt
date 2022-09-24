package dev.itssho.module.qpay.module.name.actor

import dev.itssho.module.component.resources.Strings
import dev.itssho.module.core.actor.Context
import dev.itssho.module.qpay.module.common.domain.usecase.SetModuleNameUseCase
import dev.itssho.module.qpay.module.name.presentation.QpayNameNavAction
import dev.itssho.module.shared.dialog.ui.YesNoPlatformDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun QpayNameStep(context: Context, di: QpayNameDi): QpayNameStepResult {
	val navScope = di.getNavigationScope()
	val viewModel = di.getViewModel()
	val ui = di.getUi()

	val result = suspendCoroutine<QpayNameStepResult> { continuation ->
		YesNoPlatformDialog(
			title = Strings.Name.title,
			context = context,
			okListener = { viewModel.proceed() },
			cancelListener = { viewModel.close() },
			windowCloseListener = { viewModel.close() },
		) {
			navScope.launch {
				viewModel.navAction.collect { action ->
					when (action) {
						is QpayNameNavAction.Continue -> continuation.resume(QpayNameStepResult.Name(action.name))
						is QpayNameNavAction.Close    -> continuation.resume(QpayNameStepResult.Nothing)

						is QpayNameNavAction.Nowhere  -> {}
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