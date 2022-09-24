package dev.itssho.module.component.log

import java.util.concurrent.atomic.AtomicInteger

object Logger {

	private val buffer = StringBuffer()
	private val recordCount = AtomicInteger(0)

	private val startIndex = mutableMapOf<String, Int>()
	private val endIndex = mutableMapOf<String, Int>()
}