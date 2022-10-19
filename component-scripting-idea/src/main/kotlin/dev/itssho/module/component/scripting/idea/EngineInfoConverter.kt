package dev.itssho.module.component.scripting.idea

import com.intellij.ide.script.IdeScriptEngineManager
import dev.itssho.module.component.scripting.GeneralScriptRunnerInfo

fun convertToGeneralInfo(engineInfo: IdeScriptEngineManager.EngineInfo) = GeneralScriptRunnerInfo(
	engineName = engineInfo.engineName.orEmpty(),
	engineVersion = engineInfo.engineVersion.orEmpty(),
	languageName = engineInfo.languageName.orEmpty(),
	languageVersion = engineInfo.languageVersion.orEmpty(),
	fileExtensions = engineInfo.fileExtensions ?: emptyList(),
)