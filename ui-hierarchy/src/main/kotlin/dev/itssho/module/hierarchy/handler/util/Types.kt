package dev.itssho.module.hierarchy.handler.util

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.attr.DirChain

typealias DirInterpreter = (directory: DirChain.Dir, ho: HierarchyObject, moduleName: String) -> List<String>