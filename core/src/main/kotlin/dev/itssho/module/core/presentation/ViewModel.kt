package dev.itssho.module.core.presentation

import kotlinx.coroutines.*
import kotlinx.coroutines.swing.Swing

abstract class ViewModel {

	private val scope: CoroutineScope by lazy { CoroutineScope(Job() + Dispatchers.Swing) }

	protected fun dispatchedLaunch(dispatcher: CoroutineDispatcher, errorHandler: (suspend (Throwable) -> Unit)? = null, acceptCancellationException: Boolean = false, action: suspend () -> Unit) {
		scope.launch(dispatcher) {
			doCoroutine(errorHandler, acceptCancellationException, action)
		}
	}

	protected fun launch(errorHandler: (suspend (Throwable) -> Unit)? = null, acceptCancellationException: Boolean = false, action: suspend () -> Unit) {
		scope.launch {
			doCoroutine(errorHandler, acceptCancellationException, action)
		}
	}

	private suspend fun doCoroutine(errorHandler: (suspend (Throwable) -> Unit)? = null, acceptCancellationException: Boolean = false, action: suspend () -> Unit) {
		try {
			action()
		} catch (ex: Throwable) {
			if (ex !is CancellationException || acceptCancellationException) {
				errorHandler?.invoke(ex)
			}
		}
	}

	fun finish() {
		scope.cancel()
	}
}