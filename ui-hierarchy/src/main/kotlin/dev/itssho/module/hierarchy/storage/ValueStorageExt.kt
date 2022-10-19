package dev.itssho.module.hierarchy.storage

val ValueStorage.moduleName: List<String>? get() = getList(ValueStorage.StrList.MODULE_NAME)