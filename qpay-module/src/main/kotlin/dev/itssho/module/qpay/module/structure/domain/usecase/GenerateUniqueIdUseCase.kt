package dev.itssho.module.qpay.module.structure.domain.usecase

class GenerateUniqueIdUseCase {

	operator fun invoke(): String =
	    System.currentTimeMillis().toString() + System.nanoTime().toString()
}