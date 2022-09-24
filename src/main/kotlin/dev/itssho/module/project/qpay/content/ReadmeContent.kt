package dev.itssho.module.project.qpay.content

import dev.itssho.module.project.qpay.QpayModuleTree

fun QpayModuleTree.readmeFile() {
    moduleFolder.file("README.md") { context ->
        """
                # Модуль, который отвечает за ...

                ## Owners

                [![Команда ЪУЪ](${"../".repeat(context.moduleChain.nodes.size)}teams/team_u.svg)](https://virgo.ftc.ru/pages/viewpage.action?pageId=1013724410#id-%D0%9A%D0%BE%D0%BC%D0%B0%D0%BD%D0%B4%D1%8B%E2%80%A2Teams-%D0%9A%D0%BE%D0%BC%D0%B0%D0%BD%D0%B4%D0%B0%D0%AA%D0%A3%D0%AA)

                ## Description

                Данный модуль предназначен для ...

                ## Testing-specific

                Перейти на данный экран можно, нажав на ...
            """.trimIndent()
    }
}