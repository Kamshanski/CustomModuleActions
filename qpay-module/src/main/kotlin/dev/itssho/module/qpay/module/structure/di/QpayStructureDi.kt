package dev.itssho.module.qpay.module.structure.di

import dev.itssho.module.qpay.module.structure.presentation.QpayStructureViewModel
import dev.itssho.module.qpay.module.structure.ui.StructureUi

interface QpayStructureDi {

	fun getUi(): StructureUi

	fun getViewModel(): QpayStructureViewModel
}