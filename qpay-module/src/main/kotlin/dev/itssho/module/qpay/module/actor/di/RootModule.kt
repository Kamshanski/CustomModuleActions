package dev.itssho.module.qpay.module.actor.di

import dev.itssho.module.core.actor.Context
import dev.itssho.module.core.actor.JBContext
import org.koin.core.Koin
import org.koin.dsl.bind
import org.koin.dsl.module

fun makeRootModule(jbContext: JBContext, koin: Koin) = module {
	single { jbContext } bind Context::class
	single { koin }
}