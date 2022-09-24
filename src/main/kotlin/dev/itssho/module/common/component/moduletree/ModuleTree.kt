package dev.itssho.module.common.component.moduletree

import dev.itssho.module.common.component.chain.Chain
import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.chain.any.EMPTY
import dev.itssho.module.common.component.fso.entity.Directory
import dev.itssho.module.common.component.fso.entity.ExistingFile
import dev.itssho.module.common.component.fso.entity.File
import dev.itssho.module.common.component.fso.entity.Fso

data class ModuleContext(
    val moduleChain: Chain<out Separator>,
    val companyChain: Chain<out Separator>,
    val rootChain: Chain<out Separator> = Chain.EMPTY,
)

data class ModulePath(val path: Chain<out Separator>)

abstract class ModuleTree(
    moduleChain: Chain<out Separator>,
    companyChain: Chain<out Separator>,
    codeFolderName: String = "java",
    mainResFolderName: String = "res",
    testResFolderName: String = "resources",
    rootChain: Chain<out Separator> = Chain.EMPTY,
) {
    private val filesCollector = mutableListOf<Fso>()
    private val context = ModuleContext(
        rootChain = rootChain,
        moduleChain = moduleChain,
        companyChain = companyChain
    )

    private fun path(chain: Chain<out Separator>): ModulePath = ModulePath(chain)

    abstract fun buildModuleTree(): List<Fso>
    val root = path(rootChain)
    // @formatter:off
        val moduleFolder = path(root.path + moduleChain)
            val src = path(moduleFolder.path + "src")
                val main = path(src.path + "main")
                    val mainModule = path(main.path + codeFolderName + companyChain + moduleChain)
                    val mainRes = path(main.path + mainResFolderName)
                val test = path(src.path + "test")
                    val testModule = path(test.path + codeFolderName + companyChain + moduleChain)
                    val testRes = path(test.path + testResFolderName)
    // @formatter:on

    fun ModulePath.directory(
        name: String,
        subdir: Chain<out Separator>? = null,
    ): Directory = Directory(
        path = combineNotNull(path, subdir) + name,
    ).also { filesCollector.add(it) }

    fun ModulePath.file(
        name: String,
        subdir: Chain<out Separator>? = null,
        content: (context: ModuleContext) -> String,
    ): File = File(
        path = combineNotNull(path, subdir) + name,
        content = content(context),
    ).also { filesCollector.add(it) }

    fun <T> ModulePath.editFile(
        name: String,
        subdir: Chain<out Separator>? = null,
        insertContent: T,
        content: (context: ModuleContext) -> ((fileContent: String, insertContent: T) -> String),
    ): ExistingFile<T> = ExistingFile(
        path = combineNotNull(path, subdir) + name,
        insertContent = insertContent,
        format = content(context)
    ).also { filesCollector.add(it) }

    fun exportFsos(): List<Fso> = filesCollector.toList()
}

private fun combineNotNull(ch1: Chain<out Separator>?, ch2: Chain<out Separator>?): Chain<out Separator> =
    when {
        ch1 != null && ch2 != null -> ch1 + ch2
        ch1 == null                -> ch2 ?: throw IllegalArgumentException()
        else                       -> ch1
    }