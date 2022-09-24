package dev.itssho.module.util.koin

import org.koin.core.component.KoinScopeComponent

inline fun <S : KoinScopeComponent, R> S.use(block: (S) -> R) =
	block(this)
		.also { this.closeScope() }