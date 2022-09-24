package dev.itssho.module.hierarchy

// TODO сделать на подобие Attr: Custom и Standard
enum class Act {
	DELETE,
	ADD_FILE,
	ADD_FOLDER,
	;
}

fun No(): List<Act> = emptyList()

fun AddFile(): List<Act> = listOf(Act.ADD_FILE)
fun AddFolder(): List<Act> = listOf(Act.ADD_FOLDER)
fun Delete(): List<Act> = listOf(Act.DELETE)