package dev.itssho.module.resources

object Strings {
    const val recycleServer = "Recycle Server"
    const val recycleMobile = "Recycle Mobile"
    const val party = "Party"
    const val qpay = "QPay"

    const val title = "Добавить модуль"
    const val errorTitle = "Случилась ошибка"

    object Name {

        const val title = "Имя модуля"
    }

    object Structure {

        const val title = "Папки, файлы и т.д."

        const val makeFoldersChB = "Создать папки"
        const val dontMakeFilesChB = "Создать папки"
    }

    fun failedFileCreationError(filesList: String) = "Эти файлы не получилось создать:\n$filesList.\nОстальные файлы и папки были успешно созданы/редактированны"
}