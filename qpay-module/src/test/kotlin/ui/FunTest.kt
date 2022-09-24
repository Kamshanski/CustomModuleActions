package ui

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalTime

fun main() {
	runBlocking {
		val flow = Channel<Int>(Channel.CONFLATED)

		launch {
			flow.consumeAsFlow().collect {
				println("${LocalTime.now()}: $it")
			}
		}

		launch {
            repeat(7) {
                flow.send(1)
                delay(20)
            }
		}

		delay(500)
	}
}