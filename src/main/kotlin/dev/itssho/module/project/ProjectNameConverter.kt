package dev.itssho.module.project

import dev.itssho.module.resources.R

class ProjectNameConverter {

    fun convert(type: ProjectType): String = when (type) {
        ProjectType.QPay -> R.string.qpay
        ProjectType.Recycle -> R.string.recycleServer + ". UNDER DEVELOPMENT"
        ProjectType.Party -> R.string.party + ". UNDER DEVELOPMENT"
    }
}