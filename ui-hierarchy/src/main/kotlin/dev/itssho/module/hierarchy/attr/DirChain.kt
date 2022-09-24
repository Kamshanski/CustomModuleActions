package dev.itssho.module.hierarchy.attr

data class DirChain(val chain: List<Dir>) : Attr.StandardAttr() {
	constructor(vararg chain: Dir) : this(chain.toList())

	/** Directory */
	sealed interface Dir {

		// Если в создании файлов/папок по умолчанию установть за основу PROJECT_ROOT, то в структуре модуля его можно не указывать.
		// Но так появляется возможност создавать файлы вне директории. Хз, нужно ли это.
		// Кажется, что нет(( Немножко удалил
//		object PROJECT_ROOT : Dir

		object MODULE_NAME : Dir

		/** @property delimiter на случай, если [path] будет цепочкой из нескольких вложенных папок */
		data class CUSTOM(val path: String, val delimiter: String) : Dir

		data class PERSONAL_ITEM_ID(val delimiter: String) : Dir
	}
}