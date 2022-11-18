package dev.itssho.module.qpay.module.actor.di

import dev.itssho.module.core.context.JBContext
import dev.itssho.module.core.context.ProjectWindowClickContext
import org.koin.core.Koin
import org.koin.dsl.bind
import org.koin.dsl.module

fun makeRootModule(jbContext: ProjectWindowClickContext, koin: Koin) = module {
	single { jbContext } bind JBContext::class
	single { koin }
}