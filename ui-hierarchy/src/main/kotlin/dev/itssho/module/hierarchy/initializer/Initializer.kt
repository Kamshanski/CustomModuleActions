package dev.itssho.module.hierarchy.initializer

import dev.itssho.module.hierarchy.storage.MutableValueStorage

// TODO Implement. Тут можно втащить в ValueStorage значения. Выбрасывает ошибку при редактировании констант (MODULE_NAME, PROJECT_PATH, CLICK_PATH...)
interface Initializer {

	fun initialize(valueStorage: MutableValueStorage)
}