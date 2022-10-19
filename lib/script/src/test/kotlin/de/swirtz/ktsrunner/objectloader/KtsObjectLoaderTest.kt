package de.swirtz.ktsrunner.objectloader

import de.swirtz.ktsrunner.objectloader.AssertionsExt.assertContains
import de.swirtz.ktsrunner.objectloader.AssertionsExt.assertIs
import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.script.jsr223.KotlinJsr223JvmLocalScriptEngine
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.nio.file.Files
import java.nio.file.Paths
import javax.script.ScriptEngine
import kotlin.system.measureTimeMillis

class KtsObjectLoaderTest {

	@Test
	fun `general ScriptEngineFactory test`() {
		with(KtsObjectLoader().engine.factory) {
			assertEquals(languageName, "kotlin")
			assertEquals(languageVersion, KotlinCompilerVersion.VERSION)
			assertEquals(engineName, "kotlin")
			assertEquals(engineVersion, KotlinCompilerVersion.VERSION)
			assertContains(extensions, "kts")
			assertContains(mimeTypes, "text/x-kotlin")
			assertContains(names, "kotlin")

			val methodCallSyntax = getMethodCallSyntax("obj", "method", "arg1", "arg2", "arg3")
			assertEquals(methodCallSyntax, "obj.method(arg1, arg2, arg3)")

			assertEquals(getOutputStatement("Hello, world!"), "print(\"Hello, world!\")")

			assertEquals(getParameter(ScriptEngine.LANGUAGE_VERSION), KotlinCompilerVersion.VERSION)

			val sep = System.getProperty("line.separator")
			val prog = arrayOf("val x: Int = 3", "var y = x + 2")
			assertEquals(getProgram(*prog), prog.joinToString(sep) + sep)
		}
	}

	@Test
	fun `simple evaluations should work`() {
		measureTimeMillis {
			val ktsObjectLoader: KtsObjectLoader
			measureTimeMillis {
				ktsObjectLoader = KtsObjectLoader()
			}.let { println(it) }

			val engine = ktsObjectLoader.engine

			measureTimeMillis {
				val res1 = engine.eval("val x = 3")
				assertEquals(res1, null)
			}.let { println(it) }

			measureTimeMillis {
				val res2 = engine.eval("x + 2")
				assertEquals(res2, 5)
			}.let { println(it) }

			measureTimeMillis {
				// всё ок, ошибки нет
				val fromScript = (engine as KotlinJsr223JvmLocalScriptEngine).compile("""listOf(1,2,3).joinToString(":")""")
				assertEquals(fromScript.eval(), listOf(1, 2, 3).joinToString(":"))
			}.let { println(it) }
		}.let { println(it) }
	}

	@Test
	fun `when loading expression from script it should result in an integer`() {
		val scriptContent = "5 + 10"
		assertEquals(KtsObjectLoader().load(scriptContent), 15)
	}

	@Test
	fun `when loading class from string script the content should be as expected`() {
		val scriptContent = Files.readAllBytes(Paths.get("src/test/resources/testscript.kts")).let { String(it) }

		val loaded = KtsObjectLoader().load<ClassFromScript>(scriptContent)
		assertEquals(loaded.text, "I was created in kts; äö")
		assertEquals(loaded::class, ClassFromScript::class)
	}

	@Test
	fun `when loading script with unexpected type, it should result in an IllegalArgumentException`() {
		assertThrows<ClassCastException> {
			KtsObjectLoader().load<String>("5+1")
		}
	}

	@Test
	fun `when loading script with flawed script, then a LoadException should be raised`() {
		assertThrows<ScriptEvalException> {
			KtsObjectLoader().load<Int>("Hello World")
		}
	}

	val script1 = "src/test/resources/testscript.kts"
	val script2 = "src/test/resources/testscript2.kts"

	@Test
	fun `when loading class from script via Reader the content should be as expected`() {
		val scriptContent = Files.newBufferedReader(Paths.get(script1))
		val loaded = KtsObjectLoader().load<ClassFromScript>(scriptContent)
		assertEquals(loaded.text, "I was created in kts; äö")
		assertEquals(loaded::class, ClassFromScript::class)
	}

	@Test
	fun `when loading class from script via InputStream the content should be as expected`() {
		val scriptContent = Files.newInputStream(Paths.get(script1))
		val loaded = KtsObjectLoader().load<ClassFromScript>(scriptContent)
		assertEquals(loaded.text, "I was created in kts; äö")
		assertEquals(loaded::class, ClassFromScript::class)
	}

	@Test
	fun `when loading multiple classes from script via InputStream, all should have the expected type`() {
		val scriptContent = Files.newInputStream(Paths.get(script1))
		val scriptContent2 = Files.newInputStream(Paths.get(script2))

		val classes = KtsObjectLoader().loadAll<ClassFromScript>(scriptContent, scriptContent2)
		for (clazz in classes) {
			assertIs<ClassFromScript>(clazz)
		}
	}

	@Test
	fun `when passing a custom classloader, it should be used when loading the script`() {
		val myCl = object : ClassLoader() {
			override fun loadClass(name: String?): Class<*> {
				throw IllegalStateException()
			}
		}
		assertThrows<IllegalStateException> {
			KtsObjectLoader(myCl).load("anything")
		}
	}

	@Test
	fun `load same script with class declaration EXPECT fail`() {
		val script = """
			class Telephone()
		""".trimIndent()
		val objectLoader = KtsObjectLoader()

		objectLoader.execute(script)

		assertThrows<ScriptEvalException> {
			objectLoader.execute(script)
		}
	}
}