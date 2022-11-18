package dev.itssho.module.qpay.module.selection.domain.usecase

import dev.itssho.module.qpay.module.selection.domain.entity.Script
import dev.itssho.module.qpay.module.selection.domain.repository.ScriptRepository
import dev.itssho.module.service.preferences.domain.usecase.GetSettingsUseCase
import dev.itssho.module.shared.file.domain.usecase.GetFilesInFolderUseCase
import kotlinx.coroutines.flow.Flow

class UpdateScriptsUseCase(
	private val getSettingsUseCase: GetSettingsUseCase,
	private val getFilesInFolderUseCase: GetFilesInFolderUseCase,
	private val scriptRepository: ScriptRepository,
) {

	suspend operator fun invoke(): Flow<List<Script>> {
		val scriptFolder = getSettingsUseCase().scriptPath
		val scriptFiles = getFilesInFolderUseCase(scriptFolder, ".kts")
		scriptRepository.clearAbsentItems(scriptFiles)
		val scripts = scriptRepository.load(scriptFiles)
		return scripts
	}
}
