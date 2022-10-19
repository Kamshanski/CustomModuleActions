package de.swirtz.ktsrunner.objectloader

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue

object AssertionsExt {

	fun assertContains(full: String, part: String) {
		assertTrue(full.contains(part))
	}

	fun assertContains(full: List<String>, part: String) {
		assertTrue(full.contains(part))
	}

	inline fun <reified T> assertIs(obj: Any?) {
		assertNotNull(obj)
		assertEquals(T::class, obj!!::class)
	}
}