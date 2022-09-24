package dev.itssho.module.qpay.module.create.domain.hierarchy

import dev.itssho.module.hierarchy.handler.HierarchyProcessor
import dev.itssho.module.util.koin.dev.ToBeInKoin

// TODO это должно быть репозиторием
class HierarchyProcessorProvider {

	fun get(): HierarchyProcessor {
		return QpayHierarchyProcessor()
	}
}