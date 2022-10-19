package dev.itssho.module.hierarchy.storage

val ValueStorage.moduleName: String get() = get(ValueStorage.Str.MODULE_NAME)
val ValueStorage.moduleNameOrNull: String? get() = getOrNull(ValueStorage.Str.MODULE_NAME)