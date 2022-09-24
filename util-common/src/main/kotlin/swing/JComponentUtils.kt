package swing

import javax.swing.JComponent

// TODO унести всё из этого пакета в ui-util
inline fun <reified T> JComponent.getProperty(key: String): T? = getClientProperty(key).let {
	when (it) {
		null -> null
		is T -> it
		else -> throw IllegalStateException("Param $key is not ${T::class.simpleName} but ${it::class.simpleName}")
	}
}

fun <T> JComponent.setProperty(key: String, obj: T?) {
	putClientProperty(key, obj)
}
