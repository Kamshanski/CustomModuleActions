package de.swirtz.ktsrunner.objectloader

import java.io.InputStream
import java.io.Reader
import java.util.concurrent.atomic.AtomicBoolean
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import kotlin.system.measureTimeMillis

/**
 * This class is not thread-safe, don't use it for parallel executions and create new instances instead.
 */
class KtsObjectLoader(classLoader: ClassLoader? = Thread.currentThread().contextClassLoader, warmUp: Boolean = false) {

	private val scriptEngineManager = ScriptEngineManager(classLoader)
	/**
	 * Является [KotlinJsr223JvmScriptEngineBase].
	 * Имплементации: KotlinJsr223JvmScriptEngine4Idea, KotlinJsr223JvmLocalScriptEngine
	 */
	// TODO сделать отсеивание KotlinJsr223JvmScriptEngine4Idea в пользу KotlinJsr223JvmLocalScriptEngine. Или задавать выбор параметром.
	val engine: ScriptEngine = scriptEngineManager.getEngineByExtension("kts")

	private var warmedUp = AtomicBoolean(false)

	init {
		if (warmUp) {
			warmUp()
		}
	}

	fun warmUp() {
		if (!warmedUp.getAndSet(true)) {
			val script = "1 + 1"

			val warmUpTimeMs = measureTimeMillis{
				execute(script)
			}

			println("${this::class.simpleName} warmUp executed in $warmUpTimeMs ms")
		}
	}

	inline fun <reified T> Any?.castOrError(): T = takeIf { it is T }?.let { it as T }
		?: throw ClassCastException("Cannot cast $this to expected type ${T::class}")

	inline fun <reified T> load(script: String): T =
		kotlin.runCatching { engine.eval(script) }
			.getOrElse { throw ScriptEvalException("Cannot load script", it) }
			.castOrError()

	inline fun <reified T> load(reader: Reader): T =
		kotlin.runCatching { engine.eval(reader) }
			.getOrElse { throw ScriptEvalException("Cannot load script", it) }
			.castOrError()

	@Throws(ClassCastException::class)
	inline fun <reified T> load(inputStream: InputStream): T = load(inputStream.reader())

	@Throws(ClassCastException::class)
	inline fun <reified T> loadAll(vararg inputStream: InputStream): List<T> = inputStream.map(::load)

	fun execute(script: String) {
		engine.eval(script)
	}
}