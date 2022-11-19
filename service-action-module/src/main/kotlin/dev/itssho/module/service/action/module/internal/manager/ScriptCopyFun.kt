package dev.itssho.module.service.action.module.internal.manager

import dev.itssho.module.service.action.module.domain.entity.Script

internal fun clearLoadedScript(script: Script.Loaded): Script =
	Script.Loaded(script.path, script.compilation)