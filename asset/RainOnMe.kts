import dev.itssho.module.hierarchy.initializer.ValuesInitializer
import dev.itssho.module.hierarchy.storage.MutableValueStorage

object InitializerImpl : ValuesInitializer {

	override fun initialize(valueStorage: MutableValueStorage) {
		println("Kyou mou")
		if (valueStorage.get("dare") != null) {
			valueStorage.put("dare", "hito")
		} else {
			valueStorage.putOrReplace("dare", "bakemono")
		}
	}
}

InitializerImpl