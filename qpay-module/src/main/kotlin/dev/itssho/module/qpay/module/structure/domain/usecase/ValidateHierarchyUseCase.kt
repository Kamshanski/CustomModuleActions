package dev.itssho.module.qpay.module.structure.domain.usecase

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.extension.checkActionsUniqueness
import dev.itssho.module.hierarchy.extension.checkAttributesUniqueness
import dev.itssho.module.hierarchy.extension.checkHierarchyIdsUniqueness
import dev.itssho.module.hierarchy.extension.checkPersonalIdsChars

class ValidateHierarchyUseCase {

	operator fun invoke(ho: HierarchyObject) {
		checkHierarchyIdsUniqueness(ho)
		checkPersonalIdsChars(ho)
		checkAttributesUniqueness(ho)
		checkActionsUniqueness(ho)
	}
}