package dev.itssho.module.util.koin

import org.koin.core.Koin
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

abstract class LocalKoinScope(private val _koin: Koin) : KoinScopeComponent {

	override fun getKoin(): Koin = _koin

	override val scope: Scope by lazy { createScope(this) }
}