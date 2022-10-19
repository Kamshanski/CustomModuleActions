package dev.itssho.module.hierarchy.attr

abstract class Attr internal constructor(){

	abstract val attrId: String

	open class CustomAttr(override val attrId: String): Attr()

	// TODO сделать атрибуты, которые будут определять дефолтное поведение в дефолтном хэндлере для HO. Например, атрибут AFile будет говорить, что HOFile создаст файл с именем text.
	//  По этим атрибутам можно будет проводить проверки. Например, у AFile должны быть ещё атрибуты FileExtension, Directory
	sealed class StandardAttr: Attr() {
		final override val attrId: String = this::class.simpleName!!
	}

	fun equalsAttr(other: Attr?): Boolean {
		if (this === other) return true

		if (attrId != other?.attrId) return false

		return true
	}
}
