package dev.itssho.module.qpay.module.structure.ui.delegate

import dev.itssho.module.core.presentation.ViewModel
import dev.itssho.module.hierarchy.Act
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.extension.addItem
import dev.itssho.module.hierarchy.extension.deleteItem
import dev.itssho.module.hierarchy.extension.setCheck
import dev.itssho.module.hierarchy.extension.setSelectedItem
import dev.itssho.module.hierarchy.extension.setText
import dev.itssho.module.qpay.module.structure.domain.usecase.GenerateUniqueIdUseCase
import dev.itssho.module.qpay.module.structure.domain.usecase.InitializeHierarchyUseCase
import dev.itssho.module.qpay.module.structure.domain.usecase.InterpretHierarchyTextsUseCase
import dev.itssho.module.qpay.module.structure.domain.usecase.ValidateHierarchyUseCase
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelState.Content
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelState.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TreePanelViewModel(
	private val initializeHierarchyUseCase: InitializeHierarchyUseCase,
	private val validateHierarchyUseCase: ValidateHierarchyUseCase,
	private val generateUniqueIdUseCase: GenerateUniqueIdUseCase,
	private val interpretHierarchyTextsUseCase: InterpretHierarchyTextsUseCase,
) : ViewModel() {
	private val _state = MutableStateFlow<TreePanelState>(Loading)
	val state: StateFlow<TreePanelState> = _state

	fun loadStructure() {
		val ho = initializeHierarchyUseCase()
		validateHierarchyUseCase(ho)
		_state.value = makeContent(ho)
	}

	fun handleCheckChange(ho: HierarchyObject.HOTreeCheck, selected: Boolean) {
		val currentState = (state.value as? Content)?.structure ?: return
		val newStructure = currentState.setCheck(ho.id, selected)
		_state.value = makeContent(newStructure)
	}

	fun handleTextChange(ho: HierarchyObject.HOFile, text: String) {
		val currentState = (state.value as? Content)?.structure ?: return
		val newStructure = currentState.setText(ho.id, text)
		_state.value = makeContent(newStructure)
	}

	fun handleSelectorChange(ho: HierarchyObject.HOSelector, item: String) {
		val currentState = (state.value as? Content)?.structure ?: return
		val newStructure = currentState.setSelectedItem(ho.id, item)
		_state.value = makeContent(newStructure)
	}

	fun handleAction(ho: HierarchyObject, act: Act) {
		val structure = state.value.asContent()?.structure!!
		require(ho.actions.contains(act)) { "$act is no applicable to ${ho.toSimpleString()}" }

		// TODO Добавить обработку добавления и переименования
		val newStructure = when (act) {
			Act.DELETE     -> structure.deleteItem(ho)!! // TODO добавить норм обработку удаления корневого элемента
			Act.ADD_FILE   -> structure.addItem(ho.id, HierarchyObject.HOFile(generateUniqueIdUseCase(), "", emptyList(), emptyList()))
			Act.ADD_FOLDER -> structure.addItem(ho.id, HierarchyObject.HOFile(generateUniqueIdUseCase(), "", emptyList(), emptyList()))
		}

		_state.value = makeContent(newStructure)
	}

	private fun makeContent(ho: HierarchyObject): Content {
		val texts = interpretHierarchyTextsUseCase(ho)
		return Content(ho, texts)
	}
}
