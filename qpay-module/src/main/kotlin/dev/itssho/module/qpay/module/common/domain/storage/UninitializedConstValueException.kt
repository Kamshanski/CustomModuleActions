package dev.itssho.module.qpay.module.common.domain.storage

class UninitializedConstValueException(key: String, cause: Throwable? = null) : RuntimeException("Const value '$key' is not initialized yet", cause)