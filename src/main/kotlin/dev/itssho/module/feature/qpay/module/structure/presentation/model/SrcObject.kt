package dev.itssho.module.feature.qpay.module.structure.presentation.model

// Задел на будущее для динамических штуковин
interface SrcObject {

    sealed class File(val name: String, val ext: String) : SrcObject {

        class KtFile(name: String) : File(name = name, ext = "kt")

        class XmlFile(name: String) : File(name = name, ext = "xml")
    }

    data class KtFile(var name: String) : SrcObject

    data class Folder(var name: String, val files: KtFile) : SrcObject
}

// На слое UI держать flatten holder'ы, по которым будет строиться diff (новое и старое: удлаить, отредачить, добавить)

