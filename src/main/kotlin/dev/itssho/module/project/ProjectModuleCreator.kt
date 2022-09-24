package dev.itssho.module.project

import com.intellij.openapi.project.Project

interface ProjectModuleCreator {

    fun create(project: Project, moduleName: String)
}