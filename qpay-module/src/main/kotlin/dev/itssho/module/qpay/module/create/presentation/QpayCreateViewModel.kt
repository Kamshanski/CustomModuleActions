package dev.itssho.module.qpay.module.create.presentation

import dev.itssho.module.core.presentation.ViewModel
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.extension.filterSelected
import dev.itssho.module.hierarchy.extension.flatten
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.create.domain.usecase.ImplementHierarchyUseCase
import fullStackTraceString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import string.fit
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.swing.SwingUtilities
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/** TODO добавить валидатор. Скопипастить из [com.intellij.ide.actions.CreatePackageHandler.checkInput] [com.intellij.ide.actions.CreateDirectoryOrPackageHandler.checkInput] */
class QpayCreateViewModel(
	val implementHierarchyUseCase: ImplementHierarchyUseCase,
	val valueStorage: FullyEditableValueStorage,
	val moduleAction: ModuleAction,
	val structure: HierarchyObject,
) : ViewModel() {

	private val _progress: MutableStateFlow<ProgressModel> = MutableStateFlow(ProgressModel(0, null, ""))
	val progress: StateFlow<ProgressModel> = _progress

	private val _fullLog: MutableStateFlow<StringBuilder> = MutableStateFlow(StringBuilder())
	val fullLog: StateFlow<CharSequence> = _fullLog

	private val _lastLog: MutableSharedFlow<String> = MutableSharedFlow(0)
	val lastLog: Flow<String> = _lastLog

	private val _finalResult = MutableStateFlow<Unit?>(null)
	val finalResult = _finalResult as StateFlow<Unit?>

	// TODO Чтобы тут вынести domain-логику в domain слой надо решить проблему с неправильным потоком. Но вообще вроде тут с логикой всё норм
	fun startModuleCreation() {
		launch({ handleError(it) }) {
			// TODO добавить обработку удалённого корневого элемента
			val flattenStructure = structure.filterSelected()!!.flatten()

			var errorsOccurred = false
			val max = flattenStructure.lastIndex
			flattenStructure.forEachIndexed { i, ho ->
				val percent = i * 100 / max
				val itemType = ho::class
				val itemName = ho.id

				val progress = ProgressModel(percent, itemType, itemName)
				_progress.value = progress

				publishLog("${getTimeString()}: ${itemType.simpleName}. $itemName.\n")

				// TODO не останавливать работу на ошибку
				try {
					// TODO Вытащить конструкцию в утилиты
					// TODO IDEA всё равно говорит, что редактирование ч/з PSI происходит в неправильном потоке. Сейчас implementHierarchyUseCase вызывается на потоке EWT Event что-то там @coroutine2.
					// 	Мб поток верный, но корутины в название потока впихивают свою приставку @coroutines2, отчего IDEA считает поток неверным (((
					withContext(Dispatchers.Default) {
						suspendCoroutine<Unit> { continuation ->
							SwingUtilities.invokeAndWait {
								try {
									implementHierarchyUseCase(ho)
									continuation.resume(Unit)
								} catch (ex: Throwable) {
									continuation.resumeWithException(ex)
								}
							}
						}
					}
				} catch (exception: Throwable) {
					errorsOccurred = true
					publishLog(exception.fullStackTraceString())
				}
			}

			if (errorsOccurred) {
				// TODO Добавить логирование и отправку ошибок разрабу
			} else {
				_finalResult.value = Unit
			}
		}
	}

	private fun getTimeString(): String =
		LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME).fit(12, '0')

	private suspend fun handleError(exception: Throwable) {
		val msg = exception.message + exception.stackTrace.joinToString("\n\t")
		publishLog(msg)
	}

	private suspend fun publishLog(source: CharSequence) {
		_fullLog.value.append(source)
		_lastLog.emit(source.toString())
	}

	fun close() {
		_finalResult.value = Unit
	}
}