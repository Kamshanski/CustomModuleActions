package dev.itssho.module.qpay.module.structure.presentation

import dev.itssho.module.hierarchy.Act
import dev.itssho.module.hierarchy.AddFile
import dev.itssho.module.hierarchy.AddFolder
import dev.itssho.module.hierarchy.Delete
import dev.itssho.module.hierarchy.HierarchyObject.HOFile
import dev.itssho.module.hierarchy.HierarchyObject.HOLabel
import dev.itssho.module.hierarchy.HierarchyObject.HOSelector
import dev.itssho.module.hierarchy.HierarchyObject.HOTreeCheck
import dev.itssho.module.hierarchy.attr.Attr
import dev.itssho.module.hierarchy.attr.BackText
import dev.itssho.module.hierarchy.attr.DirChain
import dev.itssho.module.hierarchy.attr.DirChain.Dir.PERSONAL_ITEM_ID
import dev.itssho.module.hierarchy.attr.DirChain.Dir.MODULE_NAME
import dev.itssho.module.hierarchy.attr.FileExtension
import dev.itssho.module.hierarchy.attr.FileTemplate
import dev.itssho.module.hierarchy.attr.FrontText
import dev.itssho.module.hierarchy.text.Text
import dev.itssho.module.qpay.module.structure.presentation.temp.template.FragmentTemplate
import dev.itssho.module.qpay.module.structure.presentation.temp.template.LayoutTemplate
import dev.itssho.module.qpay.module.structure.presentation.temp.template.ReadmeTemplate
import dev.itssho.module.qpay.module.structure.presentation.temp.template.RouterTemplate
import dev.itssho.module.qpay.module.structure.presentation.temp.template.StringsTemplate
import dev.itssho.module.qpay.module.structure.presentation.temp.template.ViewModelTemplate

private val DELIMITER = "."

private val TEAMS_LIST = listOf(
	"kukusiki",
	"UUU",
	"core",
)

// Temp File
private fun FolderItemActs(): List<Act> = AddFile() + AddFolder() + Delete()

private fun DirOfItemId(delimiter: String = DELIMITER): List<Attr> = Dir(PERSONAL_ITEM_ID(delimiter))
private fun FileExt(type: String): List<Attr> = listOf(FileExtension(type))
private fun Kt(): List<Attr> = FileExt("kt")
private fun Md(): List<Attr> = FileExt("md")
private fun Xml(): List<Attr> = FileExt("xml")

private fun Front(string: String): List<Attr> = listOf(FrontText(Text.Const(string)))
private fun Front(text: Text): List<Attr> = listOf(FrontText(text))
private fun Front(vararg any: Any): List<Attr> = any.map {
	when (it) {
		is String -> Text.Const(it)
		is Text -> it
		else -> throw IllegalArgumentException("Only String or Text allowed in Name()")
	}
}.toTypedArray().let { listOf(FrontText(Text.Complex(*it))) }

private fun Dir(string: String, delimiter: String = DELIMITER): List<Attr> = listOf(DirChain(DirChain.Dir.CUSTOM(string, delimiter)))
private fun Dir(chain: DirChain.Dir): List<Attr> = listOf(DirChain(chain))
private fun Dir(vararg any: Any, delimiter: String = DELIMITER): List<Attr> = any.map {
	when (it) {
		is String -> DirChain.Dir.CUSTOM(it, delimiter)
		is DirChain.Dir -> it
		else -> throw IllegalArgumentException("Only String or DirChain.Dir allowed in Dir()")
	}
}.toTypedArray().let { listOf(DirChain(*it)) }

private fun Back(fileExt: String): List<Attr> = listOf(BackText(Text.Const(fileExt)))
private fun Back(text: Text): List<Attr> = listOf(BackText(text))
private fun Back(vararg any: Any): List<Attr> = any.map {
	when (it) {
		is String -> Text.Const(it)
		is Text -> it
		else -> throw IllegalArgumentException("Only String or Text allowed in Name()")
	}
}.toTypedArray().let { listOf(BackText(Text.Complex(*it))) }

val struct =
	HOLabel("qpay", attributes = Front("qpay-rarog"), children = listOf(
		HOLabel("moduleName", attributes = Back(Text.Var.MODULE_NAME) + Dir(MODULE_NAME), children = listOf(
			HOTreeCheck("mainKotlin", selected = true, actions = FolderItemActs(), attrs = Back("main.kotlin") + Dir("src.main.kotlin", "ru.ftc.qpay", MODULE_NAME), children = listOf(
				HOTreeCheck("data", selected = true, actions = FolderItemActs(), attrs = Back("data") + DirOfItemId(), children = listOf(
					HOTreeCheck("datasource", selected = true, actions = FolderItemActs(), attrs = Back("datasource") + DirOfItemId()),
					HOTreeCheck("repository", selected = true, actions = FolderItemActs(), attrs = Back("repository") + DirOfItemId()),
					HOTreeCheck("convertor", selected = true, actions = FolderItemActs(), attrs = Back("convertor") + DirOfItemId()),
					HOTreeCheck("model", selected = false, actions = FolderItemActs(), attrs = Back("model") + DirOfItemId()),
					HOTreeCheck("network", selected = false, actions = FolderItemActs(), attrs = Back("network") + DirOfItemId()),
				)),
				HOTreeCheck("domain", selected = true, actions = FolderItemActs(), attrs = Back("domain") + DirOfItemId(), children = listOf(
					HOTreeCheck("repository", selected = true, actions = FolderItemActs(), attrs = Back("repository") + DirOfItemId()),
					HOTreeCheck("usecase", selected = true, actions = FolderItemActs(), attrs = Back("usecase") + DirOfItemId()),
					HOTreeCheck("scenario", selected = false, actions = FolderItemActs(), attrs = Back("scenario") + DirOfItemId()),
					HOTreeCheck("entity", selected = false, actions = FolderItemActs(), attrs = Back("entity") + DirOfItemId()),
				)),
				HOTreeCheck("presentation", selected = true, actions = FolderItemActs(), attrs = Back("presentation") + DirOfItemId(), children = listOf(
					HOTreeCheck("converter", selected = true, actions = FolderItemActs(), attrs = Back("converter") + DirOfItemId()),
					HOTreeCheck("model", selected = true, actions = FolderItemActs(), attrs = Back("model") + DirOfItemId()),
					HOFile("fileViewModel", text = "SomeModuleViewModel", actions = Delete(), attrs = Back(".kt") + FileTemplate(ViewModelTemplate()) + Kt()),
				)),
				HOTreeCheck("ui", selected = true, actions = FolderItemActs(), attrs = Back("ui") + DirOfItemId(), children = listOf(
					HOFile("fragment", text = "SomeModuleFragment", actions = Delete(), attrs = Back(".kt") + FileTemplate(FragmentTemplate()) + Kt()),
					HOFile("router", text = "SomeModuleRouter", actions = Delete(), attrs = Back(".kt") + FileTemplate(RouterTemplate()) + Kt()),
				))
			)),
			HOTreeCheck("mainRes", selected = true, attrs = Back("main.res") + Dir("src.main.res"), children = listOf(
				HOTreeCheck("layout", selected = true, actions = FolderItemActs(), attrs = Back("layout") + DirOfItemId(), children = listOf(
					HOFile("someModuleLayout", text = "SomeModuleViewModel", actions = Delete(), attrs = Back(".xml") + FileTemplate(LayoutTemplate()) + Xml()),
				)),
				HOTreeCheck("values", selected = true, actions = FolderItemActs(), attrs = Back("values") + DirOfItemId(), children = listOf(
					HOFile("strings", text = "SomeModuleViewModel", actions = Delete(), attrs = Back(".xml") + FileTemplate(StringsTemplate()) + Xml()),
				)),
			)),
			HOTreeCheck("test", selected = true, attrs = Back("test") + Dir("src.test"), children = listOf(
				HOTreeCheck("unitTest", selected = true, attrs = Back("Папки и файлы на основе main.src")),
				HOTreeCheck("mockito", selected = true, attrs = Back("Мокито")),
			)),
			HOTreeCheck("build", selected = true, attrs = Back("build.gradle")),
			HOTreeCheck("readme", selected = true, attrs = Back("readme"), children = listOf(
				HOSelector("team", items = TEAMS_LIST, selectedIndex = 0, attributes = Front("Команда") + FileTemplate(ReadmeTemplate()) + Md())
			)),
		))
	))