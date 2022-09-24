package dev.itssho.module.qpay.module.create.actor

import coroutine.justResume
import dev.itssho.module.component.resources.Strings
import dev.itssho.module.core.actor.Context
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.shared.dialog.ui.YesNoPlatformDialog
import kotlin.coroutines.suspendCoroutine

suspend fun QpayCreateStep(context: Context, structure: HierarchyObject, createDi: QpayCreateDi) {
	val title = Strings.Create.title

	createDi.insertStructure(structure)

	val viewModel = createDi.getViewModel()
	val ui = createDi.getUi()

	suspendCoroutine<Unit> { continuation ->
		val finishListener = { continuation.justResume() }

		YesNoPlatformDialog(
			title = title,
			context = context,
			okListener = finishListener,
			cancelListener = finishListener,
			windowCloseListener = finishListener,
		) {
			ui.constructView()
		}
	}

	viewModel.finish()
	ui.finish()
}