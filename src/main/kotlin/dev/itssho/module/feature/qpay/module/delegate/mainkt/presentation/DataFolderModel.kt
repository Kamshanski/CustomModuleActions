package dev.itssho.module.feature.qpay.module.delegate.mainkt.presentation

import dev.itssho.module.feature.qpay.module.common.presentation.FolderState

data class DataFolderModel(
    val isEnabled: Boolean,
    val isSelected: Boolean,
    val dataFolderState: FolderState,
    val repositoryFolderState: FolderState,
    val modelFolderState: FolderState,
    val convertorFolderState: FolderState,
    val networkFolderState: FolderState,
)