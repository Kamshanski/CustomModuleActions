package dev.itssho.module.component.resources

// TODO Утащить в соответствующие модули
object Strings {
    const val recycleServer = "Recycle Server"
    const val recycleMobile = "Recycle Mobile"
    const val party = "Party"
    const val qpay = "QPay"

    object Name {

        const val title = "Имя модуля"
    }

    object Structure {

        const val title = "Папки, файлы и т.д."

        const val makeFoldersChB = "Создать папки"
        const val dontMakeFilesChB = "Создать папки"
    }

    object Create {

        const val title = "Создание/редактирование файлов"
    }

    fun failedFileCreationError(filesList: String) = "Эти файлы не получилось создать:\n$filesList.\nОстальные файлы и папки были успешно созданы/редактированны"
}