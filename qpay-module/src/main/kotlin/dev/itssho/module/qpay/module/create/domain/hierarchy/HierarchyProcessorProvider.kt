package dev.itssho.module.qpay.module.create.domain.hierarchy

import dev.itssho.module.hierarchy.handler.HierarchyProcessor

// TODO это должно быть репозиторием (или юзкейсом с репозиторием, т.к. будет возможность использовать кэшированные и зановоскомпиленные скрипты)
class HierarchyProcessorProvider {

	fun get(): HierarchyProcessor {
		return QpayHierarchyProcessor()
	}
}