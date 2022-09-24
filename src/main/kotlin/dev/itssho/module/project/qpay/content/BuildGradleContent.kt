package dev.itssho.module.project.qpay.content

import dev.itssho.module.project.qpay.QpayModuleTree

fun QpayModuleTree.buildGradleFile() {
    moduleFolder.file("build.gradle") { _ ->
        """
                apply from: "${"$"}rootDir/napt.gradle"
                
                dependencies {
                ${"\t"}implementation(libraries.dagger)
                ${"\t"}annotationProcessor(libraries.daggerCompiler)
                ${"\t"}implementation(libraries.daggerAndroidSupport)
                
                
                ${"\t"}testImplementation(testLibraries.coreTesting)
                }
            """.trimIndent()
    }
}