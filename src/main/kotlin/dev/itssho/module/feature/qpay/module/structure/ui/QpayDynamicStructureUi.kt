package dev.itssho.module.feature.qpay.module.structure.ui

import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import dev.itssho.module.common.component.mvvm.ui.UserInterface
import dev.itssho.module.feature.qpay.module.common.presentation.HierarchyObject
import dev.itssho.module.feature.qpay.module.common.presentation.onlyAddAct
import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.dsl.label.label
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.modification.marginStart
import dev.itssho.module.uikit.dsl.select.checkBox
import dev.itssho.module.uikit.dsl.text.textField
import java.awt.Component
import javax.swing.JComponent

class QpayDynamicStructureUi(override val scope: ScopeWrapper) : UserInterface {

    companion object {
        val struct = HierarchyObject.HOLabel("qpay-rarog", "qpay", children = listOf(
            HierarchyObject.HOLabel("feature-some-module", "moduleName", children = listOf(
                HierarchyObject.HOTreeCheck("main.src", "mainSrc", isSelected = false, children = listOf(
                    HierarchyObject.HOTreeCheck("data", "data", isSelected = false, actions = onlyAddAct(), children = listOf(
                        HierarchyObject.HOTreeCheck("datasource", "datasource", isSelected = false, actions = onlyAddAct()),
                        HierarchyObject.HOTreeCheck("repository", "datasource", isSelected = false, actions = onlyAddAct()),
                        HierarchyObject.HOTreeCheck("convertor", "datasource", isSelected = false, actions = onlyAddAct()),
                        HierarchyObject.HOTreeCheck("model", "datasource", isSelected = false, actions = onlyAddAct()),
                        HierarchyObject.HOTreeCheck("network", "datasource", isSelected = false, actions = onlyAddAct()),
                    )),
                    HierarchyObject.HOTreeCheck("domain", "domain", isSelected = false, actions = onlyAddAct(), children = listOf(
                        HierarchyObject.HOTreeCheck("repository", "repository", isSelected = false, actions = onlyAddAct()),
                        HierarchyObject.HOTreeCheck("entity", "entity", isSelected = false, actions = onlyAddAct()),
                        HierarchyObject.HOTreeCheck("usecase", "usecase", isSelected = false, actions = onlyAddAct()),
                        HierarchyObject.HOTreeCheck("scenario", "scenario", isSelected = false, actions = onlyAddAct()),
                    )),
                    HierarchyObject.HOTreeCheck("presentation", "presentation", isSelected = false, actions = onlyAddAct(), children = listOf(
                        HierarchyObject.HOTreeCheck("converter", "entity", isSelected = false, actions = onlyAddAct()),
                        HierarchyObject.HOTreeCheck("model", "usecase", isSelected = false, actions = onlyAddAct()),
                        HierarchyObject.HOTreeCheck("scenario", "scenario", isSelected = false, actions = onlyAddAct()),
                        HierarchyObject.HOFile("", "fileViewModel", text = "SomeModuleViewModel"),
                    )),
                    HierarchyObject.HOTreeCheck("ui", "ui", isSelected = false, actions = onlyAddAct(), children = listOf(
                        HierarchyObject.HOTreeCheck("formatter", "formatter", isSelected = false, actions = onlyAddAct()),
                        HierarchyObject.HOFile("", "fragment", text = "SomeModuleFragment"),
                        HierarchyObject.HOFile("", "router", text = "SomeModuleRouter"),
                    ))
                )),
                HierarchyObject.HOTreeCheck("main.res", "mainRes", isSelected = false, children = listOf(
                    HierarchyObject.HOTreeCheck("layout", "layout", isSelected = false, actions = onlyAddAct(), children = listOf(
                        HierarchyObject.HOFile("", "some_module_layout", text = "SomeModuleViewModel"),
                    )),
                    HierarchyObject.HOTreeCheck("values", "values", isSelected = false, children = listOf(
                        HierarchyObject.HOFile("", "strings", text = "SomeModuleViewModel"),
                    )),
                )),
                HierarchyObject.HOTreeCheck("test", "mainRes", isSelected = false, children = listOf(
                    HierarchyObject.HOTreeCheck("Папки и файлы на основе main.src", "unitTest", isSelected = false),
                    HierarchyObject.HOTreeCheck("Мокито", "mockito", isSelected = false),
                )),
                HierarchyObject.HOTreeCheck("build.gradle", "build", isSelected = false),
                HierarchyObject.HOTreeCheck("readme", "readme", isSelected = false, children = listOf(
                    HierarchyObject.HOSelector("Команда", "team", listOf("kukusiki", "UUU", "core"), selectedIndex = 0)
                )),
            ))
        ))
    }

    private val treeViews = LinkedHashMap<String, JComponent>()

    private val layout = 5

    override fun constructView(basePanel: JXLinearPanel): JXLinearPanel = basePanel.apply {
        val initialStartShift = 8
        val increment = 16
        firstBuild(struct, initialStartShift, increment)
    }

    fun JXLinearPanel.firstBuild(tree: HierarchyObject, leftShift: Int = 0, increment: Int) {
        placeView(tree, leftShift)

        tree.children.forEach { child ->
            firstBuild(child, leftShift + increment, increment)
        }
    }

    fun JXLinearPanel.placeView(tree: HierarchyObject, leftShift: Int) {
        val modifier = Modifier.marginStart(leftShift)
        when (tree) {
            is HierarchyObject.HOTreeCheck -> checkBox(modifier, text = tree.name, selected = tree.isSelected)
            is HierarchyObject.HOFile               -> textField(modifier, text = tree.text)
            is HierarchyObject.HOLabel -> label(modifier, tree.name)
//            is HierarchyObject.HOPlainFolder        -> TODO()
//            is HierarchyObject.HOSelector           -> TODO()
            else                       -> { // skip }
            }
        }
    }
}