package dev.itssho.module.feature.qpay.module.structure.presentation.model

sealed interface Res {

    class Disabled : Res

    data class Generation(
        val strings: Boolean,
        val drawable: Boolean,
        val values: Boolean,
    )
}

sealed interface Gradle {

    class Disabled : Gradle

    data class Generation(
        val addToAppModuleBuild: Boolean,
        val addToSettings: Boolean,
        val createModuleBuild: Boolean,
    ) : Gradle
}

sealed interface ReadMe {

    class Disabled : ReadMe

    data class Generation(val generate: Boolean, val team: Team) : ReadMe

}

sealed interface Dagger {

    class Disabled : Dagger

    data class WithoutProvidesGeneration(val data: Boolean, val module: Boolean, val fragmentModule: Boolean, val api: Boolean) : Dagger

    // Пока хз, ч тут будет
    data class WithProvidesGeneration(val module: Boolean, val fragmentModule: Boolean) : Dagger
}

