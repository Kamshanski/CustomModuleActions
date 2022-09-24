package dev.itssho.module.uikit.layout.swan.linear.garbage


// Подумать над модиваером. Хочу просто цепочку. Возможно понадобится ссылка на предыдущий элемент


//data class GravityModification(val gravity: LinearGravity): Modification
//data class WeightModification(val weight: Int): Modification
//data class SizeModification(val width: Int, val height: Int): Modification
//data class MarginModification(val top: Int, val end: Int, val bottom: Int, val start: Int): Modification
//
//fun Modification.gravity(gravity: LinearGravity): Modification = this.then(GravityModification(gravity))
//
//fun Modification.size(width: Int, height: Int): Modification = this.then(SizeModification(width, height))
//
//fun Modification.margin(margin: Int): Modification = then(MarginModification(margin, margin, margin, margin))
//
//fun Modification.margin(top: Int = 0, end: Int = 0, bottom: Int = 0, start: Int = 0): Modification = then(MarginModification(top, end, bottom, start))
//
//fun Modification.weight(weight: Int): Modification = then(WeightModification(weight))
//
//open class SomeModification(private val container: Modification): Modification {
//
//    override fun add(modification: Modification): Modification {
//        TODO("Not yet implemented")
//    }
//
//    override fun <T : Modification> get(clazz: KClass<T>): T? {
//        TODO("Not yet implemented")
//    }
//}
//
//sealed interface Modification {
//
//    companion object: Modification {
//
//        override fun add(modification: Modification): Modification =
//            ModificationHolder(modification)
//
//        override fun <T : Modification> get(clazz: KClass<T>): T? {
//            throw IllegalAccessException("Cannot get modification from companion")
//        }
//    }
//
//    fun add(modification: Modification): Modification
//
//    fun <T: Modification> get(clazz: KClass<T>): T?
//
//}
//
//private class ModificationHolder(modification: Modification): Modification {
//
//    private val modifiers = mutableMapOf<KClass<out Modification>, Modification>().apply {
//        put(modification::class, modification)
//    }
//
//    override fun add(modification: Modification): Modification {
//        modifiers[modification::class] = modification
//        return this
//    }
//
//    override fun <T : Modification> get(clazz: KClass<T>): T? =
//        modifiers[clazz] as? T
//}