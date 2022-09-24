package dev.itssho.module.qpay.domain

import dev.itssho.module.qpay.data.core.QpayFileCreator

class CreateDataFoldersUseCase(
    val qpayFileCreator: QpayFileCreator,
) {

    operator fun invoke(
        model: Boolean = false,
        datasource: Boolean = false,
        converter: Boolean = false,
        network: Boolean = false,
        repository: Boolean = false,
    ) {
//        qpayFileCreator.creator

    }
}