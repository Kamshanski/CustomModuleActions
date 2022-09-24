package dev.itssho.module.feature.qpay.module.delegate.mainkt.ui

import dev.itssho.module.feature.qpay.module.delegate.mainkt.presentation.DataFolderDelegate
import dev.itssho.module.feature.qpay.module.delegate.mainkt.presentation.DataFolderViewModel
import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.modification.marginStart
import dev.itssho.module.uikit.modification.marginVertical
import dev.itssho.module.uikit.modification.wrapHeight
import dev.itssho.module.uikit.dsl.panel.column
import dev.itssho.module.uikit.dsl.panel.row
import dev.itssho.module.uikit.dsl.select.checkBox

class DataFolderUi() {

    private val viewModel = DataFolderViewModel()
    val delegate: DataFolderDelegate = viewModel

    fun install(rootPanel: JXLinearPanel, folderMargin: Int, panelVerticalMargin: Int) = rootPanel.apply {
        column(Modifier.wrapHeight().marginStart(folderMargin).marginVertical(panelVerticalMargin)) {
            checkBox(text = "domain")
            row {
                checkBox(Modifier.marginStart(folderMargin), text = "entity")
                checkBox(Modifier.marginStart(folderMargin), text = "repository")
                checkBox(Modifier.marginStart(folderMargin), text = "usecase")
                checkBox(Modifier.marginStart(folderMargin), text = "scenario")
            }
        }
    }
}