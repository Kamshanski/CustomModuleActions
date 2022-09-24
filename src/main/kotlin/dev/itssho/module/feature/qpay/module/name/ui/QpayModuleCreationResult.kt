package dev.itssho.module.feature.qpay.module.name.ui

import dev.itssho.module.common.component.chain.Chain
import dev.itssho.module.common.component.chain.Separator

sealed interface QpayModuleCreationResult {

    data class Module(val chain: Chain<Separator.Minus>): QpayModuleCreationResult

    class Nothing(): QpayModuleCreationResult
}