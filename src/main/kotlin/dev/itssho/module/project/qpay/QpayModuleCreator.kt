package dev.itssho.module.project.qpay

import com.intellij.openapi.project.Project
import dev.itssho.module.common.component.chain.Chain
import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.chain.splitToChain
import dev.itssho.module.common.component.fso.FsoCreator
import dev.itssho.module.project.ProjectModuleCreator
import dev.itssho.module.resources.R
import dev.itssho.module.util.filterThrown
import java.io.IOException

class QpayModuleCreator(
    projectRootChain: Chain<out Separator>,
) : ProjectModuleCreator {

    private val creator = FsoCreator(projectRootChain)

    override fun create(project: Project, moduleName: String) {
        val moduleChain = moduleName.splitToChain(QPAY_SEPARATOR)
        val structure = QpayModuleTree(moduleChain, QPAY_COMPANY_CHAIN)
        val fsos = structure.buildModuleTree()

        val failedFso = fsos.filterThrown { fso ->
            creator.create(fso)
        }
            .entries
        if (failedFso.isNotEmpty()) {
            val reportMsg = failedFso.joinToString(separator = ";\n") { (fso, exception) ->
                fso.pathChain.toString() + ": " + (exception.localizedMessage ?: exception.message ?: exception::class.simpleName)
            }
            throw IOException(R.string.failedFileCreationError(reportMsg))
        }

    }
}