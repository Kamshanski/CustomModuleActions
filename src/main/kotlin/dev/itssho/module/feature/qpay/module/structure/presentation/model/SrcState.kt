package dev.itssho.module.feature.qpay.module.structure.presentation.model


sealed interface Src {

    class Disabled : Src

    data class FoldersGeneration(
        val data: Data,
        val domain: Domain,
        val presentation: Presentation,
        val ui: Ui,
        val destination: File,
    ) : Src
}

sealed interface Data {

    class Disabled : Data

    data class Generate(
        val datasource: Folder,
        val repository: Folder,
        val converter: Folder,
        val model: Folder,
        val network: Folder,
    ) : Data
}

sealed interface Domain {

    class Disabled : Domain

    data class Generate(
        val entity: Folder,
        val repository: Folder,
        val usecase: Folder,
        val scenario: Folder,
    ) : Domain
}

sealed interface Presentation {

    class Disabled : Presentation

    data class Generate(
        val converter: Folder,
        val model: Folder,
        val viewModel: File,
    ) : Presentation
}

sealed interface Ui {

    class Disabled : Ui

    data class Generate(
        val formatter: Folder,
        val router: File,    // Включает создание RouterImpl
        val fragment: File,
        val adapter: File,
        val viewHolder: File,
    ) : Ui
}

sealed interface Folder {

    class Disabled : Folder

    class Generation(val generate: Boolean, val files: List<String>) : Folder
}

sealed interface File {

    class Disabled : File

    class Generation(val generate: Boolean) : File
}