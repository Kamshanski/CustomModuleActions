package dev.itssho.module.service.action.module.internal.concurency

import dev.itssho.module.service.action.module.internal.concurency.SafeThread.Companion.makeSafeThread
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.Semaphore

/** Гарантирует, что каждый запрос будет выполнен на новом потоке. Кол-во одновременных потоков ограничено */
internal class NewSafeThreadExecutor(limit: Int) {

	val threadLimit = limit.coerceIn(1, 20)
	private var queueThread: SafeThread? = null

	private val lock = Semaphore(threadLimit)
	private val queue = LinkedBlockingQueue<SafeThread.() -> Unit>(10000)
	private val threadCache = LinkedBlockingQueue<SafeThread>(threadLimit)

	fun start() = synchronized(this) {
		if (queueThread == null) {
			queueThread = makeSafeThread { mainQueueLoop() }
			queueThread?.start()
		}
	}

	private fun mainQueueLoop() {
		while (true) {
			val runnable = queue.take()
			lock.acquire()

			val thread = makeSafeThread {
				try {
					runnable.invoke(this)
				}
				finally {
					threadCache.remove(this)
					lock.release()
				}
			}
			thread.name = "Script Compilation Thread [${thread.name}]"

			thread.start()
		}
	}

	fun clearInactiveThreads() {
		threadCache.removeIf { !it.isAlive }
	}

	fun addToQueue(runnable: SafeThread.() -> Unit) {
		queue.put(runnable)
	}

	fun addToQueue(runnableList: List<SafeThread.() -> Unit>) {
		runnableList.onEach { queue.put(it) }
	}

	/** Blocks current thread to wail finish of all active threads */
	fun stop() {
		queueThread?.interrupt()
		threadCache.onEach { it.interrupt() }

		threadCache.onEach { it.join() }
		queueThread?.join()
	}
}