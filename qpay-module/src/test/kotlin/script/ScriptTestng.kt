package script

import de.swirtz.ktsrunner.objectloader.KtsObjectLoader
import dev.itssho.module.hierarchy.initializer.ValuesInitializer
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path

// Тест для проверки способностей скриптов. Не unit-тест
class ScriptTesting {

	@Test
	fun `basic eval (DanceInTheDark)`() {
		val filePath 	= "C:\\_Coding\\InteliJ Idea\\ModuleCreator\\asset\\DanceInTheDark.kts".let { Path.of(it) }
		val inputStream = Files.newInputStream(filePath)
		val objLoader 	= KtsObjectLoader()
		val sb 			= objLoader.load<StringBuilder>(inputStream)
		val text		= sb.toString()

		assertEquals(text, "jie du shi")
	}

	@Test
	fun `basic eval with hierarchy class (RainOnMe)`() {
		val filePath 		= "C:\\_Coding\\InteliJ Idea\\ModuleCreator\\asset\\RainOnMe.kts".let { Path.of(it) }
		val inputStream 	= Files.newInputStream(filePath)

		val objLoader 		= KtsObjectLoader()
		val initializer	= objLoader.load<ValuesInitializer>(inputStream)
		//val ctx = objLoader.engine.context

		val storage 		= FullyEditableValueStorage()
		initializer.initialize(storage)
		val dare			= storage.get("dare")

		assertEquals(dare, "hito")
	}

	@Test
	fun `eval script with hierarchy class, unload script classes, eval same script EXPECT no errors (RainOnMe)`() {
		val filePath 		= "C:\\_Coding\\InteliJ Idea\\ModuleCreator\\asset\\RainOnMe.kts".let { Path.of(it) }
		val scriptText 		= Files.readString(filePath)

		val objLoader1 		= KtsObjectLoader()
		val initializer1	= objLoader1.load<ValuesInitializer>(scriptText)

		val objLoader2 		= KtsObjectLoader()
		val initializer2	= objLoader2.load<ValuesInitializer>(scriptText)

		val storage 		= FullyEditableValueStorage()
		initializer1.initialize(storage)
		initializer2.initialize(storage)
		val dare			= storage.get("dare")

		assertEquals(dare, "bakemono")
	}
}