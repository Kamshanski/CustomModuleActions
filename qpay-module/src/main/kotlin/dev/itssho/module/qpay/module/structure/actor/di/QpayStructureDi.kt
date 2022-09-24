package dev.itssho.module.qpay.module.structure.actor.di

import dev.itssho.module.qpay.module.structure.presentation.QpayStructureViewModel
import dev.itssho.module.qpay.module.structure.ui.QpayStructureUi
import kotlinx.coroutines.CoroutineScope

interface QpayStructureDi {

	fun getUi(): QpayStructureUi

	fun getViewModel(): QpayStructureViewModel

	fun getNavigationScope(): CoroutineScope
}