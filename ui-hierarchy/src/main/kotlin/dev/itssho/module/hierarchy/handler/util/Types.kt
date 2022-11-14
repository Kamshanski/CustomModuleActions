package dev.itssho.module.hierarchy.handler.util

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.attr.Directory
import dev.itssho.module.hierarchy.storage.ValueStorage

typealias DirInterpreter = (directory: Directory.Chain, ho: HierarchyObject, valueStorage: ValueStorage) -> List<String>