package coroutine

import kotlin.coroutines.Continuation

public inline fun Continuation<Unit>.justResume(): Unit =
	resumeWith(Result.success(Unit))