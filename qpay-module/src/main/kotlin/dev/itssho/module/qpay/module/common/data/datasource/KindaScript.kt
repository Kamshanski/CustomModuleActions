package dev.itssho.module.qpay.module.common.data.datasource

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
import dev.itssho.module.hierarchy.initializer.HierarchyInitializer
import dev.itssho.module.hierarchy.initializer.ValuesInitializer
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.hierarchy.storage.moduleName
import dev.itssho.module.hierarchy.text.Text

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
		valueStorage.putOrReplace("team", ho.selectedItem)
		val parent = ho.parent
		val directory = DirUtil.extractDirRecursively(parent!!, valueStorage)

		val extension = FileUtil.getFileExtensionPart(ho)
		val fileName = FileUtil.getFileName(ho)
		val fullFileName = fileName + extension

		val content = FileUtil.getContent(ho, directory, fileName, extension, valueStorage)

		controller.createFile(directory, fullFileName, content)
	}
}

class ValuesInitializerImpl : ValuesInitializer {

	override fun initialize(valueStorage: MutableValueStorage) {

	}
}

class FragmentTemplate : FileTemplate.Template("FRAGMENT_TEMPLATE_NAME") {

	override fun compile(folder: List<String>, fileName: String, fileExtension: String, valueStorage: ValueStorage): String =
		"""
			package ${folder.joinToString(separator = ".")}
			
			import java.lang.Runnable

			class $fileName(

			) : Runnable() {
				
				override fun run() { 
					// TODO Fragment
				}
			}
			
		""".trimIndent()
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
			#Team: ${valueStorage.get("team")!!}
			
			
			# Dillinger
			## _The Last Markdown Editor, Ever_
			
			[![N|Solid](https://cldup.com/dTxpPi9lDf.thumb.png)](https://nodesource.com/products/nsolid)
			
			[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)
			
			Dillinger is a cloud-enabled, mobile-ready, offline-storage compatible,
			AngularJS-powered HTML5 Markdown editor.
			
			- Type some Markdown on the left
			- See HTML in the right
			- ✨Magic ✨
			
			## Features
			
			- Import a HTML file and watch it magically convert to Markdown
			- Drag and drop images (requires your Dropbox account be linked)
			- Import and save files from GitHub, Dropbox, Google Drive and One Drive
			- Drag and drop markdown and HTML files into Dillinger
			- Export documents as Markdown, HTML and PDF
			
			Markdown is a lightweight markup language based on the formatting conventions
			that people naturally use in email.
			As [John Gruber] writes on the [Markdown site][df1]
			
			> The overriding design goal for Markdown's
			> formatting syntax is to make it as readable
			> as possible. The idea is that a
			> Markdown-formatted document should be
			> publishable as-is, as plain text, without
			> looking like it's been marked up with tags
			> or formatting instructions.
			
			This text you see here is *actually- written in Markdown! To get a feel
			for Markdown's syntax, type some text into the left window and
			watch the results in the right.
			
			## Tech
			
			Dillinger uses a number of open source projects to work properly:
			
			- [AngularJS] - HTML enhanced for web apps!
			- [Ace Editor] - awesome web-based text editor
			- [markdown-it] - Markdown parser done right. Fast and easy to extend.
			- [Twitter Bootstrap] - great UI boilerplate for modern web apps
			- [node.js] - evented I/O for the backend
			- [Express] - fast node.js network app framework [@tjholowaychuk]
			- [Gulp] - the streaming build system
			- [Breakdance](https://breakdance.github.io/breakdance/) - HTML
			to Markdown converter
			- [jQuery] - duh
			
			And of course Dillinger itself is open source with a [public repository][dill]
			 on GitHub.
			
			## Installation
			
			Dillinger requires [Node.js](https://nodejs.org/) v10+ to run.
			
			Install the dependencies and devDependencies and start the server.
			
			```sh
			cd dillinger
			npm i
			node app
			```
			
			For production environments...
			
			```sh
			npm install --production
			NODE_ENV=production node app
			```
			
			## Plugins
			
			Dillinger is currently extended with the following plugins.
			Instructions on how to use them in your own application are linked below.
			
			| Plugin | README |
			| ------ | ------ |
			| Dropbox | [plugins/dropbox/README.md][PlDb] |
			| GitHub | [plugins/github/README.md][PlGh] |
			| Google Drive | [plugins/googledrive/README.md][PlGd] |
			| OneDrive | [plugins/onedrive/README.md][PlOd] |
			| Medium | [plugins/medium/README.md][PlMe] |
			| Google Analytics | [plugins/googleanalytics/README.md][PlGa] |
			
			## Development
			
			Want to contribute? Great!
			
			Dillinger uses Gulp + Webpack for fast developing.
			Make a change in your file and instantaneously see your updates!
			
			Open your favorite Terminal and run these commands.
			
			First Tab:
			
			```sh
			node app
			```
			
			Second Tab:
			
			```sh
			gulp watch
			```
			
			(optional) Third:
			
			```sh
			karma test
			```
			
			#### Building for source
			
			For production release:
			
			```sh
			gulp build --prod
			```
			
			Generating pre-built zip archives for distribution:
			
			```sh
			gulp build dist --prod
			```
			
			## Docker
			
			Dillinger is very easy to install and deploy in a Docker container.
			
			By default, the Docker will expose port 8080, so change this within the
			Dockerfile if necessary. When ready, simply use the Dockerfile to
			build the image.
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
			<string name="name">Strings</string>
			<string name="ext">xml</string>
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
					// TODO Fragment
				}
			}
			
		""".trimIndent()
}

class HierarchyInitializerImpl : HierarchyInitializer {

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
										HierarchyObject.HOTreeCheck("network", selected = false, actions = FolderItemActs(), attrs = Back("network") + DirOfItemId()),
									)
								),
								HierarchyObject.HOTreeCheck(
									"domain", selected = true, actions = FolderItemActs(), attrs = Back("domain") + DirOfItemId(), children = listOf(
										HierarchyObject.HOTreeCheck("repository", selected = true, actions = FolderItemActs(), attrs = Back("repository") + DirOfItemId()),
										HierarchyObject.HOTreeCheck("usecase", selected = true, actions = FolderItemActs(), attrs = Back("usecase") + DirOfItemId()),
										HierarchyObject.HOTreeCheck("scenario", selected = false, actions = FolderItemActs(), attrs = Back("scenario") + DirOfItemId()),
										HierarchyObject.HOTreeCheck("entity", selected = false, actions = FolderItemActs(), attrs = Back("entity") + DirOfItemId()),
									)
								),
								HierarchyObject.HOTreeCheck(
									"presentation", selected = true, actions = FolderItemActs(), attrs = Back("presentation") + DirOfItemId(), children = listOf(
										HierarchyObject.HOTreeCheck("converter", selected = true, actions = FolderItemActs(), attrs = Back("converter") + DirOfItemId()),
										HierarchyObject.HOTreeCheck("model", selected = true, actions = FolderItemActs(), attrs = Back("model") + DirOfItemId()),
										HierarchyObject.HOFile("fileViewModel", text = VMText(vs), actions = Delete(), attrs = Back(".kt") + FileTemplate(ViewModelTemplate()) + Kt()),
									)
								),
								HierarchyObject.HOTreeCheck(
									"ui", selected = true, actions = FolderItemActs(), attrs = Back("ui") + DirOfItemId(), children = listOf(
										HierarchyObject.HOFile("fragment", text = FramgentText(vs), actions = Delete(), attrs = Back(".kt") + FileTemplate(FragmentTemplate()) + Kt()),
										HierarchyObject.HOFile("router", text = RouterText(vs), actions = Delete(), attrs = Back(".kt") + FileTemplate(RouterTemplate()) + Kt()),
									)
								)
							)
						),
						HierarchyObject.HOTreeCheck(
							"mainRes", selected = true, attrs = Back("main.res") + Dir("src.main.res"), children = listOf(
								HierarchyObject.HOTreeCheck(
									"layout", selected = true, actions = FolderItemActs(), attrs = Back("layout") + DirOfItemId(), children = listOf(
										HierarchyObject.HOFile("Layout", text = LayoutText(vs), actions = Delete(), attrs = Back(".xml") + FileTemplate(LayoutTemplate()) + Xml()),
									)
								),
								HierarchyObject.HOTreeCheck(
									"values", selected = true, actions = FolderItemActs(), attrs = Back("values") + DirOfItemId(), children = listOf(
										HierarchyObject.HOFile("strings", text = "strings_ewallet", actions = Delete(), attrs = Back(".xml") + FileTemplate(StringsTemplate()) + Xml()),
									)
								),
							)
						),
						HierarchyObject.HOTreeCheck(
							"test", selected = true, attrs = Back("test") + Dir("src.test"), children = listOf(
								HierarchyObject.HOTreeCheck("unitTest", selected = true, attrs = Back("Папки и файлы на основе main.src")),
								HierarchyObject.HOTreeCheck("mockito", selected = true, attrs = Back("Мокито")),
							)
						),
						HierarchyObject.HOTreeCheck("build", selected = true, attrs = Back("build.gradle")),
						HierarchyObject.HOTreeCheck(
							"readme", selected = true, attrs = Back("readme"), children = listOf(
								HierarchyObject.HOSelector("team", items = TEAMS_LIST, selectedIndex = 0, attributes = Front("Команда") + FileTemplate(ReadmeTemplate()) + Md())
							)
						),
					)
				)
			)
		)
	}
}


private val DELIMITER = "."

private val TEAMS_LIST = listOf(
	"kukusiki",
	"UUU",
	"core",
)

// File Text Initializer

val moduleNamePascalCasePrefix = { vs: ValueStorage ->
	vs.moduleName!!
		.let { it.subList(1, it.size) }
		.map { it.toLowerCaseAsciiOnly() }
		.joinToString(separator = "") { it[0].toUpperCase() + it.substring(startIndex = 1) }
}
val moduleNameSnakeCasePrefix = { vs: ValueStorage ->
	vs.moduleName!!
		.let { it.subList(1, it.size) }
		.map { it.toLowerCaseAsciiOnly() }
		.joinToString(separator = "_")
}
private fun VMText(vs: ValueStorage) = "${moduleNamePascalCasePrefix(vs)}ViewModel"
private fun FramgentText(vs: ValueStorage) = "${moduleNamePascalCasePrefix(vs)}Fragment"
private fun RouterText(vs: ValueStorage) = "${moduleNamePascalCasePrefix(vs)}Router"
private fun LayoutText(vs: ValueStorage) = "${moduleNameSnakeCasePrefix(vs)}_fragment"

// Temp File
private fun FolderItemActs(): List<Act> = AddFile() + AddFolder() + Delete()

private fun DirOfItemId(delimiter: String = DELIMITER): List<Attr> = Dir(DirChain.Dir.PERSONAL_ITEM_ID(delimiter))
private fun FileExt(type: String): List<Attr> = listOf(FileExtension(type))
private fun Kt(): List<Attr> = FileExt("kt")
private fun Md(): List<Attr> = FileExt("md")
private fun Xml(): List<Attr> = FileExt("xml")

private fun Front(string: String): List<Attr> = listOf(FrontText(Text.Const(string)))
private fun Front(text: Text): List<Attr> = listOf(FrontText(text))
private fun Front(vararg any: Any): List<Attr> = any.map {
	when (it) {
		is String -> Text.Const(it)
		is Text   -> it
		else      -> throw IllegalArgumentException("Only String or Text allowed in Name()")
	}
}.toTypedArray().let { listOf(FrontText(Text.Complex(*it))) }

private fun Dir(string: String, delimiter: String = DELIMITER): List<Attr> = listOf(DirChain(DirChain.Dir.CUSTOM(string, delimiter)))
private fun Dir(chain: DirChain.Dir): List<Attr> = listOf(DirChain(chain))
private fun Dir(vararg any: Any, delimiter: String = DELIMITER): List<Attr> = any.map {
	when (it) {
		is String       -> DirChain.Dir.CUSTOM(it, delimiter)
		is DirChain.Dir -> it
		else            -> throw IllegalArgumentException("Only String or DirChain.Dir allowed in Dir()")
	}
}.toTypedArray().let { listOf(DirChain(*it)) }

private fun Back(fileExt: String): List<Attr> = listOf(BackText(Text.Const(fileExt)))
private fun Back(text: Text): List<Attr> = listOf(BackText(text))
private fun Back(vararg any: Any): List<Attr> = any.map {
	when (it) {
		is String -> Text.Const(it)
		is Text   -> it
		else      -> throw IllegalArgumentException("Only String or Text allowed in Name()")
	}
}.toTypedArray().let { listOf(BackText(Text.Complex(*it))) }

fun String.toLowerCaseAsciiOnly(): String {
	val builder = StringBuilder(this.length)
	for (i in this.indices) {
		var lowedChar: Char

		val c = this[i]
		if (c in 'A'..'Z') {
			lowedChar = Character.toLowerCase(c)
		} else {
			lowedChar = c
		}
		builder.append(lowedChar)
	}
	return builder.toString()
}
