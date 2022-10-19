package script

import dev.itssho.module.hierarchy.Act
import dev.itssho.module.hierarchy.AddFile
import dev.itssho.module.hierarchy.AddFolder
import dev.itssho.module.hierarchy.Delete
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.attr.Attr
import dev.itssho.module.hierarchy.attr.BackText
import dev.itssho.module.hierarchy.attr.DirChain
import dev.itssho.module.hierarchy.attr.FileExtension
import dev.itssho.module.hierarchy.attr.FileTemplate
import dev.itssho.module.hierarchy.attr.FrontText
import dev.itssho.module.hierarchy.controller.Controller
import dev.itssho.module.hierarchy.extension.selectedItem
import dev.itssho.module.hierarchy.handler.HierarchyProcessor
import dev.itssho.module.hierarchy.handler.util.DirUtil
import dev.itssho.module.hierarchy.handler.util.FileUtil
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.initializer.HierarchyInitializer
import dev.itssho.module.hierarchy.initializer.ValuesInitializer
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.hierarchy.storage.moduleName
import dev.itssho.module.hierarchy.text.Text
import java.util.Locale

/* Util functions. Attention!: Some fun usage may cause exceptions. Avoid global fun.  */

fun Char.uppercase(): Char = this.toString().lowercase().first()
@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")	// Независимость от версии Kotlin`а
fun String.uppercase(): String = (this as java.lang.String).toUpperCase(Locale.US)
fun Char.lowercase(): Char = this.toString().lowercase().first()
@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")	// Независимость от версии Kotlin`а
fun String.lowercase(): String = (this as java.lang.String).toLowerCase(Locale.US)



/* Value Initializer. Keys and values initialization here  */

object Keys {

	const val COMPANY_NAME = "COMPANY_NAME"

	const val TEAM = "TEAM"
}

class ValuesInitializerImpl : ValuesInitializer {

	override fun initialize(valueStorage: MutableValueStorage) {
		valueStorage.put(Keys.COMPANY_NAME, listOf("ru", "ftc", "qpay"))
	}
}



/* Hierarchy Processor. Use controller to create folders, files or edit them. Use FileTemplate.Template to make dynamic file content.  */

class QpayHierarchyProcessor : HierarchyProcessor() {

	override fun handle(ho: HierarchyObject, valueStorage: MutableValueStorage, controller: Controller) {
		when (ho) {
			is HierarchyObject.HOFile      -> {
				super.handle(ho, valueStorage, controller)
			}

			is HierarchyObject.HOLabel     -> {}

			is HierarchyObject.HOSelector  -> {
				when {
					ho.id.contains("team") -> handleTeam(ho, valueStorage, controller)
					else                   -> super.handle(ho, valueStorage, controller)
				}
			}

			is HierarchyObject.HOTreeCheck -> {
				when {
					ho.id.contains("mockito")  -> handleMockito(ho)
					ho.id.contains("unitTest") -> handleUnitTest(ho)
					ho.id.contains("build")    -> handleBuild(ho)
					ho.id.contains("readme")   -> handleReadme(ho)
					else                       -> super.handle(ho, valueStorage, controller)
				}
			}
		}
	}

	private fun handleMockito(ho: HierarchyObject.HOTreeCheck) {
		println("Skip mockito")
	}

	private fun handleUnitTest(ho: HierarchyObject.HOTreeCheck) {
		println("Skip unitTest")
	}

	private fun handleBuild(ho: HierarchyObject.HOTreeCheck) {
		println("Skip build")
	}

	private fun handleReadme(ho: HierarchyObject.HOTreeCheck) {
		println("Skip build")
	}

	private fun handleTeam(ho: HierarchyObject.HOSelector, valueStorage: MutableValueStorage, controller: Controller) {
		valueStorage.putOrReplace(Keys.TEAM, ho.selectedItem)
		val parent = ho.parent
		val directory = DirUtil.extractDirRecursively(parent!!, valueStorage)

		val extension = FileUtil.getFileExtensionPart(ho)
		val fileName = FileUtil.getFileName(ho)
		val fullFileName = fileName + extension

		val content = FileUtil.getContent(ho, directory, fileName, extension, valueStorage)

		controller.createFile(directory, fullFileName, content)
	}
}

class FragmentTemplate : FileTemplate.Template("FRAGMENT_TEMPLATE_NAME") {

	override fun compile(folder: List<String>, fileName: String, fileExtension: String, valueStorage: ValueStorage): String {
		val companyName = valueStorage.getList(Keys.COMPANY_NAME)
		val moduleName = valueStorage.moduleName
		val fqPackage = companyName + moduleName

		return """
			package ${fqPackage.joinToString(separator = ".")}

			import java.lang.Runnable

			class $fileName(

			) : Runnable() {

				override fun run() {
					// TODO Fragment
				}
			}

		""".trimIndent()
	}
}

class LayoutTemplate : FileTemplate.Template("LAYOUT_TEMPLATE_NAME") {

	override fun compile(folder: List<String>, fileName: String, fileExtension: String, valueStorage: ValueStorage): String =
		"""
			<SomeTag>
			<!--> Layout file <-->
			</SomeTag>
		""".trimIndent()
}

class ReadmeTemplate : FileTemplate.Template("README_TEMPLATE_NAME") {

	override fun compile(folder: List<String>, fileName: String, fileExtension: String, valueStorage: ValueStorage): String =
		"""
			#Team: ${valueStorage.getOrNull(Keys.TEAM)}

			- ✨Magic ✨
		""".trimIndent()
}

class RouterTemplate : FileTemplate.Template("ROUTER_TEMPLATE_NAME") {

	override fun compile(folder: List<String>, fileName: String, fileExtension: String, valueStorage: ValueStorage): String =
		"""
			package ${folder.joinToString(separator = ".")}

			interface $fileName {

				fun open() {
					// TODO Router
				}
			}

		""".trimIndent()
}

class StringsTemplate : FileTemplate.Template("STRINGS_TEMPLATE_NAME") {

	override fun compile(folder: List<String>, fileName: String, fileExtension: String, valueStorage: ValueStorage): String =
		"""
			<string name="str1">Hello</string>
			<string name="str2">Module</string>
		""".trimIndent()
}

class ViewModelTemplate : FileTemplate.Template("VIEW_MODEL_TEMPLATE_NAME") {

	override fun compile(folder: List<String>, fileName: String, fileExtension: String, valueStorage: ValueStorage): String =
		"""
			package ${folder.joinToString(separator = ".")}

			import java.lang.Runnable

			class $fileName(

			) : Runnable() {

				override fun run() {
					TODO("Fragment")
				}
			}

		""".trimIndent()
}



/* Hierarchy Initializer. Define initial hierarchy. Use attrs to add features to each element: design, file name/ext/templates and more */

@Suppress("MemberVisibilityCanBePrivate", "PropertyName", "TestFunctionName")
class HierarchyInitializerImpl : HierarchyInitializer {

	val DELIMITER = "."

	val TEAMS_LIST = listOf(
		"kukusiki",
		"UUU",
		"core"
	)

	fun moduleNamePascalCasePrefix(vs: ValueStorage): String {
		return vs.moduleName
			.let { it.subList(1, it.size) }
			.map { it.lowercase() }
			.joinToString(separator = "") { it[0].uppercase() + it.substring(startIndex = 1) }
	}

	fun moduleNameSnakeCasePrefix(vs: ValueStorage): String {
		return vs.moduleName
			.let { it.subList(1, it.size) }
			.joinToString(separator = "_") { it.lowercase() }
	}

	fun VMText(vs: ValueStorage) = "${moduleNamePascalCasePrefix(vs)}ViewModel"
	fun FragmentText(vs: ValueStorage) = "${moduleNamePascalCasePrefix(vs)}Fragment"
	fun RouterText(vs: ValueStorage) = "${moduleNamePascalCasePrefix(vs)}Router"
	fun LayoutText(vs: ValueStorage) = "${moduleNameSnakeCasePrefix(vs)}_fragment"

	// Temp File
	fun FolderItemActs(): List<Act> = AddFile() + AddFolder() + Delete()

	fun DirOfItemId(delimiter: String = DELIMITER): List<Attr> = Dir(DirChain.Dir.PERSONAL_ITEM_ID(delimiter))
	fun FileExt(type: String): List<Attr> = listOf(FileExtension(type))
	fun Kt(): List<Attr> = FileExt("kt")
	fun Md(): List<Attr> = FileExt("md")
	fun Xml(): List<Attr> = FileExt("xml")

	fun Front(str: String): List<Attr> = listOf(FrontText(Text.Const(str)))
	fun Front(text: Text): List<Attr> = listOf(FrontText(text))
	fun Front(vararg any: Any): List<Attr> = any.map {
		when (it) {
			is String -> Text.Const(it)
			is Text   -> it
			else      -> throw IllegalArgumentException("Only String or Text allowed in Name()")
		}
	}.toTypedArray().let { listOf(FrontText(Text.Complex(*it))) }

	fun Dir(string: String, delimiter: String = DELIMITER): List<Attr> = listOf(DirChain(DirChain.Dir.CUSTOM(string, delimiter)))
	fun Dir(chain: DirChain.Dir): List<Attr> = listOf(DirChain(chain))
	fun Dir(vararg any: Any, delimiter: String = DELIMITER): List<Attr> = any.map {
		when (it) {
			is String       -> DirChain.Dir.CUSTOM(it, delimiter)
			is DirChain.Dir -> it
			else            -> throw IllegalArgumentException("Only String or DirChain.Dir allowed in Dir()")
		}
	}.toTypedArray().let { listOf(DirChain(*it)) }

	fun Back(fileExt: String): List<Attr> = listOf(BackText(Text.Const(fileExt)))
	fun Back(text: Text): List<Attr> = listOf(BackText(text))
	fun Back(vararg any: Any): List<Attr> = any.map {
		when (it) {
			is String -> Text.Const(it)
			is Text   -> it
			else      -> throw IllegalArgumentException("Only String or Text allowed in Name()")
		}
	}.toTypedArray().let { listOf(BackText(Text.Complex(*it))) }

	override fun initialize(valueStorage: ValueStorage): HierarchyObject = valueStorage.let { vs ->
		HierarchyObject.HOLabel(
			"qpay", attributes = Front("qpay-rarog"), children = listOf(
				HierarchyObject.HOLabel(
					"moduleName", attributes = Back(Text.Var.MODULE_NAME) + Dir(DirChain.Dir.MODULE_NAME), children = listOf(
						HierarchyObject.HOTreeCheck(
							"mainKotlin", selected = true, actions = FolderItemActs(), attrs = Back("main.kotlin") + Dir("src.main.kotlin", "ru.ftc.qpay", DirChain.Dir.MODULE_NAME), children = listOf(
								HierarchyObject.HOTreeCheck(
									"data", selected = true, actions = FolderItemActs(), attrs = Back("data") + DirOfItemId(), children = listOf(
										HierarchyObject.HOTreeCheck("datasource", selected = true, actions = FolderItemActs(), attrs = Back("datasource") + DirOfItemId()),
										HierarchyObject.HOTreeCheck("repository", selected = true, actions = FolderItemActs(), attrs = Back("repository") + DirOfItemId()),
										HierarchyObject.HOTreeCheck("convertor", selected = true, actions = FolderItemActs(), attrs = Back("convertor") + DirOfItemId()),
										HierarchyObject.HOTreeCheck("model", selected = false, actions = FolderItemActs(), attrs = Back("model") + DirOfItemId()),
										HierarchyObject.HOTreeCheck("network", selected = false, actions = FolderItemActs(), attrs = Back("network") + DirOfItemId())
									)
								),
								HierarchyObject.HOTreeCheck(
									"domain", selected = true, actions = FolderItemActs(), attrs = Back("domain") + DirOfItemId(), children = listOf(
										HierarchyObject.HOTreeCheck("repository", selected = true, actions = FolderItemActs(), attrs = Back("repository") + DirOfItemId()),
										HierarchyObject.HOTreeCheck("usecase", selected = true, actions = FolderItemActs(), attrs = Back("usecase") + DirOfItemId()),
										HierarchyObject.HOTreeCheck("scenario", selected = false, actions = FolderItemActs(), attrs = Back("scenario") + DirOfItemId()),
										HierarchyObject.HOTreeCheck("entity", selected = false, actions = FolderItemActs(), attrs = Back("entity") + DirOfItemId())
									)
								),
								HierarchyObject.HOTreeCheck(
									"presentation", selected = true, actions = FolderItemActs(), attrs = Back("presentation") + DirOfItemId(), children = listOf(
										HierarchyObject.HOTreeCheck("converter", selected = true, actions = FolderItemActs(), attrs = Back("converter") + DirOfItemId()),
										HierarchyObject.HOTreeCheck("model", selected = true, actions = FolderItemActs(), attrs = Back("model") + DirOfItemId()),
										HierarchyObject.HOFile("fileViewModel", text = VMText(vs), actions = Delete(), attrs = Back(".kt") + FileTemplate(ViewModelTemplate()) + Kt())
									)
								),
								HierarchyObject.HOTreeCheck(
									"ui", selected = true, actions = FolderItemActs(), attrs = Back("ui") + DirOfItemId(), children = listOf(
										HierarchyObject.HOFile("fragment", text = FragmentText(vs), actions = Delete(), attrs = Back(".kt") + FileTemplate(FragmentTemplate()) + Kt()),
										HierarchyObject.HOFile("router", text = RouterText(vs), actions = Delete(), attrs = Back(".kt") + FileTemplate(RouterTemplate()) + Kt())
									)
								)
							)
						),
						HierarchyObject.HOTreeCheck(
							"mainRes", selected = true, attrs = Back("main.res") + Dir("src.main.res"), children = listOf(
								HierarchyObject.HOTreeCheck(
									"layout", selected = true, actions = FolderItemActs(), attrs = Back("layout") + DirOfItemId(), children = listOf(
										HierarchyObject.HOFile("Layout", text = LayoutText(vs), actions = Delete(), attrs = Back(".xml") + FileTemplate(LayoutTemplate()) + Xml())
									)
								),
								HierarchyObject.HOTreeCheck(
									"values", selected = true, actions = FolderItemActs(), attrs = Back("values") + DirOfItemId(), children = listOf(
										HierarchyObject.HOFile("strings", text = "strings_ewallet", actions = Delete(), attrs = Back(".xml") + FileTemplate(StringsTemplate()) + Xml())
									)
								)
							)
						),
						HierarchyObject.HOTreeCheck(
							"test", selected = true, attrs = Back("test") + Dir("src.test"), children = listOf(
								HierarchyObject.HOTreeCheck("unitTest", selected = true, attrs = Back("Папки и файлы на основе main.src")),
								HierarchyObject.HOTreeCheck("mockito", selected = true, attrs = Back("Мокито"))
							)
						),
						HierarchyObject.HOTreeCheck("build", selected = true, attrs = Back("build.gradle")),
						HierarchyObject.HOTreeCheck(
							"readme", selected = true, attrs = Back("readme"), children = listOf(
								HierarchyObject.HOSelector("team", items = TEAMS_LIST, selectedIndex = 0, attributes = Front("Команда") + FileTemplate(ReadmeTemplate()) + Md())
							)
						)
					)
				)
			)
		)
	}
}



/* Combine all classes in one script return */

ModuleAction(
	name = "MyGreatName",
	hierarchyInitializer = HierarchyInitializerImpl(),
	valuesInitializer = ValuesInitializerImpl(),
	hierarchyProcessor = QpayHierarchyProcessor()
)