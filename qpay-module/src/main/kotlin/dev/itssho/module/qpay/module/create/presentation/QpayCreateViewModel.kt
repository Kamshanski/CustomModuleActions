package dev.itssho.module.qpay.module.create.presentation

import dev.itssho.module.core.presentation.ViewModel
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.extension.filterSelected
import dev.itssho.module.hierarchy.extension.flatten
import dev.itssho.module.qpay.module.create.domain.usecase.ImplementHierarchyUseCase
import dev.itssho.module.shared.file.domain.usecase.MakeIdeaFileUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import string.fit
import java.io.OutputStream
import java.io.PrintStream
import java.io.PrintWriter
import java.io.StringWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/** TODO добавить валидатор. Скопипастить из [com.intellij.ide.actions.CreatePackageHandler.checkInput] [com.intellij.ide.actions.CreateDirectoryOrPackageHandler.checkInput] */
class QpayCreateViewModel(
	val implementHierarchyUseCase: ImplementHierarchyUseCase,
	val makeIdeaFileUseCase: MakeIdeaFileUseCase,
	val structure: HierarchyObject,
) : ViewModel() {

	private val _progress: MutableStateFlow<ProgressModel> = MutableStateFlow(ProgressModel(0, null, ""))
	val progress: StateFlow<ProgressModel> = _progress

	private val _fullLog: MutableStateFlow<StringBuilder> = MutableStateFlow(StringBuilder())
	val fullLog: StateFlow<CharSequence> = _fullLog

	private val _lastLog: MutableSharedFlow<String> = MutableSharedFlow(0)
	val lastLog: Flow<String> = _lastLog

	fun startModuleCreation() {
		launch({ handleError(it) }) {
			// TODO добавить обработку удалённого корневого элемента
			val flattenStructure = structure.filterSelected()!!.flatten()
			val logBuilder = StringBuilder()

			var errorsOccured: Boolean = false
			val max = flattenStructure.lastIndex
			flattenStructure.forEachIndexed { i, ho ->
				val percent = i * 100 / max

				val progress = ProgressModel(percent, ho::class, ho.id)

				logBuilder.apply {
					append(getTimeString())
					append(": ")
					append(progress.itemType?.simpleName)
					append(". ")
					append(progress.itemName)
					append(".\n")
				}

				_progress.value = progress
				publishLog(logBuilder)
				logBuilder.clear()

				// TODO не останавливать работу на ошибку
				try {
					implementHierarchyUseCase(ho)
				} catch (exception: Throwable) {
					errorsOccured = true
					logBuilder.apply {
						if (_fullLog.value.isNotEmpty()) {
							append("\n")
						}
						val writer = StringWriter()
						exception.printStackTrace(PrintWriter(writer))
						logBuilder.append(writer.toString())
					}

					publishLog(logBuilder)
					logBuilder.clear()
				}
			}

			if (errorsOccured) {
				makeIdeaFileUseCase("Log_${LocalDateTime.now().run { "$hour.$minute.$second" }}.txt", _fullLog.value.toString())
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
}