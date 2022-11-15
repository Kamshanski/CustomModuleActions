package dev.itssho.module.qpay.module.selection.di

import dev.itssho.module.qpay.module.selection.presentation.SelectionViewModel
import dev.itssho.module.qpay.module.selection.ui.SelectionUi

interface SelectionDi {

	fun getViewModel(): SelectionViewModel

	fun getUi(): SelectionUi
}