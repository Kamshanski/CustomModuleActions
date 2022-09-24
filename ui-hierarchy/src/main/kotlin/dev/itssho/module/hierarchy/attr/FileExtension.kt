package dev.itssho.module.hierarchy.attr

// TODO FileExtension без самого файла бесполезен. Унести в [FileTemplate]
// TODO Мб можно заменить на FileType вместо String, если domain слой открыть в разработку. Наверное при делёжке модуля это вынесется
class FileExtension(string: String) : Attr.StandardAttr() {

	val string: String = string.trimStart('.')

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is FileExtension) return false

		if (string != other.string) return false

		return true
	}

	override fun hashCode(): Int {
		return string.hashCode()
	}

	override fun toString(): String {
		return "FileExtension(string='$string')"
	}
}
