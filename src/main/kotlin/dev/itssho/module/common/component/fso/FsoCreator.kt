package dev.itssho.module.common.component.fso

import dev.itssho.module.common.component.chain.Chain
import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.chain.walk
import dev.itssho.module.common.component.fso.entity.Directory
import dev.itssho.module.common.component.fso.entity.ExistingFile
import dev.itssho.module.common.component.fso.entity.File
import dev.itssho.module.common.component.fso.entity.Fso
import dev.itssho.module.util.toPath
import java.nio.file.Files
import java.nio.file.Path

class FsoCreator(
    private val modulesRootPathChain: Chain<out Separator>,
) {

    fun create(fso: Fso) {
        when (fso) {
            is Directory       -> createFoldersRecursively(fso)
            is File            -> createFile(fso)
            is ExistingFile<*> -> editFile(fso)
        }
    }

    @Throws(IllegalStateException::class)
    private fun editFile(fso: ExistingFile<*>) {
        val path = fso.pathChain.toFullPath()
        if (!Files.exists(path)) {
            throw IllegalStateException("File to edit is absent")
        }

        val fileContent = Files.readString(path)
        val editedContent = fso.formatContent(fileContent)
        Files.write(path, editedContent.toByteArray())
    }

    @Throws(Exception::class)
    private fun createFile(fso: File) {
        createFoldersRecursively(fso.directory)
        val path = fso.pathChain.toFullPath()
        if (Files.exists(path)) {
            Files.delete(path)
        }
        Files.createFile(path)
        Files.write(path, fso.content.toByteArray())
    }

    private fun createFoldersRecursively(fso: Directory) {
        fso.pathChain.walk { chain ->
            val path = chain.toFullPath()
            if (!Files.isReadable(path)) {
                Files.createDirectory(path)
            }
        }
    }

    fun Chain<out Separator>.toFullPath(): Path = (modulesRootPathChain + this).toPath()
}