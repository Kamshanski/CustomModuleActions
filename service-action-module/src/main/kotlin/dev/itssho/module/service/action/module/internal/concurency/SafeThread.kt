package dev.itssho.module.service.action.module.internal.concurency

import java.util.concurrent.atomic.AtomicBoolean

internal class SafeThread constructor(target: Runnable) : Thread(target) {

	private val shouldStopFlag = AtomicBoolean(false)

	companion object {

		fun <T> makeSafeThreadForResult(
			name: String? = null,
			isDaemon: Boolean = true,
			onStarted: () -> Unit = {},
			onSuccess: (T) -> Unit = {},
			onError: (Throwable) -> Unit = {},
			onFinally: () -> Unit = {},
			action: SafeThread.() -> T,
		): SafeThread = SafeThread(
			target = {
				val thread = currentThread() as SafeThread
				if (thread.shouldStop()) {
					onFinally()
					return@SafeThread
				}

				try {
					onStarted()
					if (thread.shouldStop()) return@SafeThread

					val result = thread.action()
					if (thread.shouldStop()) return@SafeThread

					onSuccess(result)
				} catch (ex: Throwable) {
					if (thread.shouldStop()) return@SafeThread
					onError(ex)
					throw ex
				} finally {
					onFinally()
				}
			}
		).also {
			it.isDaemon = isDaemon
			if (name != null) {
				it.name = name
			}
		}

		inline fun makeSafeThread(
			name: String? = null,
			isDaemon: Boolean = true,
			noinline onStarted: () -> Unit = {},
			crossinline onSuccess: () -> Unit = {},
			noinline onError: (Throwable) -> Unit = {},
			noinline onFinally: () -> Unit = {},
			noinline action: SafeThread.() -> Unit,
		): SafeThread = makeSafeThreadForResult(name, isDaemon, onStarted, { onSuccess() }, onError, onFinally, action)
	}

	fun shouldStop(): Boolean =
		shouldStopFlag.get()

	fun requestStop() {
		shouldStopFlag.set(true)
	}

	override fun interrupt() {
		requestStop()
		super.interrupt()
	}
}