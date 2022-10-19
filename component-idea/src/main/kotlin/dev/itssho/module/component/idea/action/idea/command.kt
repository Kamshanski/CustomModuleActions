package dev.itssho.module.component.idea.action.idea

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.application.ModalityState.NON_MODAL
import com.intellij.openapi.application.invokeAndWaitIfNeeded
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.project.Project

/**
 * Использует [invokeAndWaitIfNeeded], т.к. иначе выбрасывает некритичную ошибку (текст ниже).
 * Не знаю, что такое [ModalityState], но [ModalityState.current()] вроде использует текущее диалоговое окно. Пусть пока так (TODO разобраться)
 *
 * Write-unsafe context! Model changes are allowed from write-safe contexts only.
 * Please ensure you're using invokeLater/invokeAndWait with a correct modality state (not "any").
 * See TransactionGuard documentation for details.
 */
internal inline fun invokeAndWaitForWriteCommand(project: Project, commandKey: String, groupId: String? = "Create Qpay Module", modalityState: ModalityState? = NON_MODAL, crossinline block: () -> Unit) {
	val command = Runnable {
		val run = Runnable {
			block()
		}
		ApplicationManager.getApplication().runWriteAction(run)
	}

	invokeAndWaitIfNeeded(modalityState) {
		CommandProcessor.getInstance().executeCommand(
			project,
			command,
			commandKey,
			groupId, // TODO Потестить отмену создани модуля. Возможно (UPD. точно нужен) для этой операции нужен groupId.
			// UPD2 Опытным путём это не помогло. Нужно обратить внимание на LocalHistory.
			// Если не поможет, то собирать на уровне domain все таски для Idea и отдельно запускать их всем скопом в invokeAndWaitIfNeeded
		)
	}
}