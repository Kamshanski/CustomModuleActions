package dev.itssho.module.service.action.module.internal.manager

import coroutine.DuplicableMutableStateFlow
import dev.itssho.module.service.action.module.domain.entity.Script
import kotlinx.coroutines.flow.StateFlow
import java.nio.file.Path

// TODO Убрать декор. Хватит поля getStateFlow и modify
internal class ScriptsFlow(
	val actualState: DuplicableMutableStateFlow<Map<Path, Script>> = DuplicableMutableStateFlow(HashMap())
) : StateFlow<Map<Path, Script>> by actualState {

	val lock = Any()

	inline fun modify(modifyAction: (Map<Path, Script>) -> Map<Path, Script>) = synchronized(lock) {
		val currentVal = actualState.value
		val result = modifyAction(currentVal)
		actualState.value = result
	}

//	override suspend fun collect(collector: FlowCollector<Map<Path, ActionItem>>): Nothing {
//		actualState.gather { map -> collector.emit(HashMap(map)) }
//	}

	inline fun immutable(): StateFlow<Map<Path, Script>> = this
}