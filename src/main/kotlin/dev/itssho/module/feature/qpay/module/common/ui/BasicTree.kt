package dev.itssho.module.feature.qpay.module.common.ui

import dev.itssho.module.feature.qpay.module.common.presentation.HierarchyObject.*
import dev.itssho.module.feature.qpay.module.common.presentation.onlyAddAct

val QpayStruct =
    HOLabel("qpay-rarog", "qpay", children = listOf(
        HOLabel("feature-some-module", "moduleName", children = listOf(
            HOTreeCheck("main.src", "mainSrc", isSelected = false, actions = listOf(), children = listOf(
                HOTreeCheck("data", "data", isSelected = false, actions = onlyAddAct(), children = listOf(
                    HOTreeCheck("datasource", "datasource", isSelected = false, actions = onlyAddAct()),
                    HOTreeCheck("repository", "datasource", isSelected = false, actions = onlyAddAct()),
                    HOTreeCheck("convertor", "datasource", isSelected = false, actions = onlyAddAct()),
                    HOTreeCheck("model", "datasource", isSelected = false, actions = onlyAddAct()),
                    HOTreeCheck("network", "datasource", isSelected = false, actions = onlyAddAct()),
                )),
                HOTreeCheck("domain", "domain", isSelected = false, actions = onlyAddAct(), children = listOf(
                    HOTreeCheck("repository", "repository", isSelected = false, actions = onlyAddAct()),
                    HOTreeCheck("entity", "entity", isSelected = false, actions = onlyAddAct()),
                    HOTreeCheck("usecase", "usecase", isSelected = false, actions = onlyAddAct()),
                    HOTreeCheck("scenario", "scenario", isSelected = false, actions = onlyAddAct()),
                )),
                HOTreeCheck("presentation", "presentation", isSelected = false, actions = onlyAddAct(), children = listOf(
                    HOFile("", "fileViewModel", text = "SomeModuleViewModel"),
                    HOTreeCheck("converter", "entity", isSelected = false, actions = onlyAddAct()),
                    HOTreeCheck("model", "usecase", isSelected = false, actions = onlyAddAct()),
                    HOTreeCheck("scenario", "scenario", isSelected = false, actions = onlyAddAct()),
                )),
                HOTreeCheck("ui", "ui", isSelected = false, actions = onlyAddAct(), children = listOf(
                    HOFile("", "fragment", text = "SomeModuleViewModel"),
                    HOFile("", "router", text = "SomeModuleRouter"),
                    HOFile("", "fileViewModel", text = "SomeModuleViewModel"),
                    HOTreeCheck("formatter", "formatter", isSelected = false, actions = onlyAddAct()),
                ))
            )),
            HOTreeCheck("main.res", "mainRes", isSelected = false, actions = listOf(), children = listOf(
                HOTreeCheck("layout", "layout", isSelected = false, actions = onlyAddAct(), children = listOf(
                    HOFile("", "some_module_layout", text = "SomeModuleViewModel"),
                )),
                HOTreeCheck("values", "values", isSelected = false, actions = onlyAddAct(), children = listOf(
                    HOFile("", "strings", text = "SomeModuleViewModel"),
                )),
            )),
            HOTreeCheck("test", "mainRes", isSelected = false, actions = listOf(), children = listOf(
                HOTreeCheck("Папки и файлы на основе main.src" , "unitTest", isSelected = false),
                HOTreeCheck("Мокито", "mockito", isSelected = false),
            )),
            HOFinalCheck("build.gradle", "build", isSelected = false),
            HOTreeCheck("readme", "readme", isSelected = false, children = listOf(
                HOSelector("Команда", "team", listOf("kukusiki", "UUU", "core"), selectedIndex = 0)
            )),
        ))
    ))