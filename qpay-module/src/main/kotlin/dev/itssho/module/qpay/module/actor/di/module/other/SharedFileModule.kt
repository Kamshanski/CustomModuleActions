package dev.itssho.module.qpay.module.actor.di.module.other

import dev.itssho.module.shared.file.domain.usecase.MakeIdeaFileUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun makeSharedFileModule() = module {
	factoryOf(::MakeIdeaFileUseCase)
}