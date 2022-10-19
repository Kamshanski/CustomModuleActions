package dev.itssho.module.hierarchy.handler.util

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.attr.Directory

typealias DirInterpreter = (directory: Directory.Chain, ho: HierarchyObject, moduleName: String) -> List<String>