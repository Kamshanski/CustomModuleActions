package dev.itssho.module.core.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

abstract class DialogUI<T>(
	val scope: CoroutineScope,
) {

	private var result: StepResult = StepResult.None

	abstract var width: Int
	abstract var height: Int
	abstract var title: String
	abstract fun addOkListener(listener: () -> Unit)
	abstract fun addCancelListener(listener: () -> Unit)
	abstract fun addWindowCloseListener(listener: () -> Unit)

	abstract fun initialize()

	fun showForResult(): T {
		initialize()

		onShow()

		val presentResult = result as? StepResult.Present ?: throw ClassCastException("StepResult is not Present ${this::class.simpleName} on Dialog Resume")
		val result = presentResult.value as T

		onDispose()

		return result
	}

	fun setDialogResult(result: T) {
		onResume()
		this.result = StepResult.Present(result)
	}

	open fun onShow() {}

	open fun onResume() {}

	open fun onDispose() {}

	open fun onFinish() {}

	open fun finish() {
		scope.cancel()
		onFinish()
	}

	private sealed interface StepResult {

		class Present(val value: Any?) : StepResult

		object None : StepResult
	}
}