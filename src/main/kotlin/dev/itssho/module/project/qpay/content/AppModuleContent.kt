package dev.itssho.module.project.qpay.content

import dev.itssho.module.common.component.chain.*
import dev.itssho.module.project.qpay.QPAY_SEPARATOR
import dev.itssho.module.project.qpay.QpayModuleTree
import dev.itssho.module.util.file.FileSlice
import dev.itssho.module.util.file.blockOfSimilar

fun QpayModuleTree.appModuleFiles(moduleChain: Chain<out Separator>) {
    root.editFile("settings.gradle", insertContent = moduleChain.castTo(Separator.Minus)) { _ ->
        format@{ fileContent, insertContent ->


            val fileLines = fileContent.lines()
            var slice: FileSlice? = null
            var searchChain = insertContent
            while (slice == null && searchChain.nodes.isNotEmpty()) {
                slice = blockOfSimilar(fileLines, searchChain.toString())
                if (slice == null) {
                    searchChain = searchChain.removeLast(1)
                }
            }

            if (slice == null) {
                return@format fileContent
            }

            val newModuleLine =
                "\t${"':${insertContent.castTo(QPAY_SEPARATOR)}'".padEnd(53)}: '${insertContent.castTo(Separator.Url)}',"

            var insertIndex = slice.last
            for (i in slice.first..slice.last) {
                if (newModuleLine < fileLines[i]) {
                    insertIndex = i
                    break
                }
            }

            val newFileLines = fileLines.toMutableList().apply { add(insertIndex, newModuleLine) }

            return@format newFileLines.joinToString(separator = "\n")
        }
    }

    root.editFile(name = "build.gradle", insertContent = moduleChain.castTo(Separator.Minus), subdir = Separator.Minus.chainOf("app")) { _ ->
        format@{ fileContent, insertContent ->

            fun blockOfSimilar(lines: List<String>, content: String): FileSlice? {
                val first = lines.indexOfFirst { it.contains(content) }
                val last = lines.indexOfLast { it.contains(content) }
                return if (first != -1 && last != -1) {
                    FileSlice(first, last)
                } else {
                    null
                }
            }

            val fileLines = fileContent.lines()
            var foundSlice: FileSlice? = null
            var searchChain = insertContent
            while (foundSlice == null && searchChain.nodes.isNotEmpty()) {
                foundSlice = blockOfSimilar(fileLines, searchChain.toString())
                if (foundSlice == null) {
                    searchChain = searchChain.removeLast(1)
                }
            }

            val slice = foundSlice ?: return@format fileContent

            val newModuleLine = "\timplementation(project(\":${moduleChain.castTo(Separator.Minus)}\"))"

            var insertIndex = slice.last
            for (i in slice.first..slice.last) {
                if (newModuleLine < fileLines[i]) {
                    insertIndex = i
                    break
                }
            }

            val newFileLines = fileLines.toMutableList().apply { add(insertIndex, newModuleLine) }

            return@format newFileLines.joinToString(separator = "\n")
        }
    }
}