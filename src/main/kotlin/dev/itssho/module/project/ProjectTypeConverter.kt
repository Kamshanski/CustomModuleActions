package dev.itssho.module.project

class ProjectTypeConverter() {

    fun convert(type: ProjectType): String {
        return type.name
    }

    fun revert(type: String): ProjectType =
        try {
            ProjectType.valueOf(type)
        } catch (iae: IllegalArgumentException) {
            throw IllegalArgumentException("Project type '$type' is not found")
        }
}