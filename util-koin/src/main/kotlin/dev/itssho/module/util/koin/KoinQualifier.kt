package dev.itssho.module.util.koin

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.ext.getFullName

abstract class KoinQualifier(customValue: String? = null): Qualifier {

	override val value: QualifierValue = customValue ?: this::class.java.name
}
