package dev.itssho.module.hierarchy.controller

// Не имплементируй это. Это для плагина
interface Controller {

	/** Создаёт файл в папке [directory] с именем [fileName] и текстом [content]
	 * @param directory Путь до файла. Не создаётся при отсутствии (TODO уточнить)
	 * @param fileName Имя файла (включая расширение). Например, "ViewModel.java"
	 * @param content Строка с содержанием файла
	 */
	fun createFile(directory: List<String>, fileName: String, content: String)

	/** Создаёт папки по пути [directory]. Т.е. все промежуточные папки в [directory] будут созданы в случае отсутствия */
	fun createDirectory(directory: List<String>)

	/** Переименовывает последнюю папку в пути [rootDirectory] новым имененм [newDirName]
	 * Для дерева проекта
	 * src
	 *   |- kotlin
	 *   		 |- dev
	 *   		 	  |- company
	 * [rootDirectory] = ["src", "kotlin", "dev"]
	 * и
	 * [newDirName] = "release"
	 * результатом будет
	 * src
	 *   |- kotlin
	 *   		 |- release
	 *   		 	  	  |- company
	 * NB! Это не переименование пакета. package в файлах не будет изменён
	 * */
	fun editDirectoryName(rootDirectory: List<String>, newDirName: String)

	/** Изменяет имя файла по пути [directory]. Имена файлов включают расширения файлов (например, "ViewModel.java") */
	fun editFileName(directory: List<String>, oldFileName: String, newFileName: String)

	/** Перезаписывает текст из файла [fileName] (имя включает расширение файла) по пути [directory].
	 * В лямбду [edit] передаётся старый контент файла, а на выходе получается ноывй. Если лямбда возвращает [null], то конктент остаётся прежним */
	fun editFileContent(directory: List<String>, fileName: String, edit: (String) -> String?)

	/** Удаляет файл */
	fun deleteFile(directory: List<String>, fileName: String)

	/** Удаляет последнюю папку в пути [directory]. Если папки не существует, то пофиг. Если существует, то всё, что внутри папки вместе с самой папкой удаляется. */
	fun deleteDirectory(directory: List<String>)
}