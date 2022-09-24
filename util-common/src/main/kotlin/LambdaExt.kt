fun List<() -> Unit>.invokeAll() {
	forEach { it.invoke() }
}