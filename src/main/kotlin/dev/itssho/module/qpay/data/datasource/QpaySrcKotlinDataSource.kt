package dev.itssho.module.qpay.data.datasource

import dev.itssho.module.common.component.chain.Chain
import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.qpay.data.core.QpayProjectContext

class QpaySrcKotlinDataSource(
    private val projectContext: QpayProjectContext,
) {

    fun createFile(pathAndName: Chain<out Separator>, content: String) {

    }

    fun createFolder(path: Chain<out Separator>) {

    }
}