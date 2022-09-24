package dev.itssho.module.project.qpay

import dev.itssho.module.common.component.chain.Chain
import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.fso.entity.Fso
import dev.itssho.module.common.component.moduletree.*
import dev.itssho.module.project.qpay.content.*

class QpayModuleTree(moduleChain: Chain<out Separator>, companyChain: Chain<out Separator>) : ModuleTree(moduleChain, companyChain) {

    override fun buildModuleTree(): List<Fso> {
        dataFolders()
        domainFolders()
        presentationFolders()
        uiFolders()

        resourcesFolders()

        buildGradleFile()
        readmeFile()

        manifestFile()

        mockitoFile()

        appModuleFiles(moduleFolder.path)

        return exportFsos()
    }
}