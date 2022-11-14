package dev.itssho.module.service.action.module.internal.concurency

import java.util.concurrent.ExecutorService

// TODO Унести в util модуль. Убрать internal.
internal inline fun <T> ExecutorService.submitWithListeners(
	crossinline onStarted: () -> Unit = {},
	crossinline onSuccess: (T) -> Unit = {},
	crossinline onError: (Throwable) -> Unit = {},
	crossinline onFinally: () -> Unit = {},
	crossinline action: () -> T,
) = submit {
	try {
		onStarted()
		val result = action()
		onSuccess(result)
	} catch (ex: Throwable) {
		onError(ex)
		throw ex
	} finally {
		onFinally()
	}
}