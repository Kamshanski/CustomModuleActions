package dev.itssho.module.service.action.module.internal.concurency

import java.util.concurrent.locks.Lock

// TODO Унести в util модуль. Убрать internal.
internal  inline fun Lock.execute(action: () -> Unit) {
	lock()
	try {
		action()
	} finally {
		unlock()
	}

}

internal  inline fun <T> Lock.compute(action: () -> T): T {
	lock()
	try {
		return action()
	} finally {
		unlock()
	}
}

internal inline fun Lock.tryExecute(action: () -> Unit) {
	if (tryLock()) {
		try {
			action()
		} finally {
			unlock()
		}
	}
}

internal inline fun <T> Lock.tryCompute(action: () -> T, default: () -> T): T {
	if (tryLock()) {
		try {
			return action()
		} finally {
			unlock()
		}
	} else {
		return default()
	}
}

internal inline fun <T> Lock.tryCompute(action: () -> T, default: T): T =
	tryCompute(action, { default })