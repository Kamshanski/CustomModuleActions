package sandbox

import dev.getCallPlace

data class Viva(val int: Int, val string: String) {

}
class Sandbox() {

	fun pipa(int: Int, string: String) {

		fun popa(int: Int, string: String) {
			println(getCallPlace(int, string, Viva(int, string), listOf(5.5, 89)))
		}

		popa(int, string)
	}
}

fun main() {
	val p = Sandbox()
	p.pipa(5, "99\n88\n\t77\n")
}