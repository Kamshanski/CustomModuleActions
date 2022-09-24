package ui

import common.runTestFrame
import dev.itssho.module.core.actor.Context
import dev.itssho.module.core.actor.SwingContext
import dev.itssho.module.hierarchy.controller.Controller
import dev.itssho.module.qpay.module.actor.QpayModuleWizardActor
import dev.itssho.module.qpay.module.create.domain.repository.DirectoryRepository
import dev.itssho.module.qpay.module.create.domain.repository.FileRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import string.filter
import string.truncate
import javax.swing.JPanel

@ExtendWith()
class Test : KoinTest {

	@RegisterExtension
	val mockProvider = MockProviderExtension.create { clazz ->
		// Setup your nock framework
		Mockito.mock(clazz.java)
	}

	@Test
	fun ttt() = runBlocking {
		runTestFrame {
			val swingContext = SwingContext(this)
			val actor = QpayModuleWizardActor(swingContext = swingContext)
			actor.di.apply {
				declareMock<Context> {
					SwingContext(this@runTestFrame)
				}
				declareMock<Controller> {
					object : Controller {
						override fun createFile(directory: List<String>, fileName: String, content: String) {
							println(">>> createFile '${directory.joinToString()}', '$fileName', '${content.truncate(20).filter('\n', '\t')}'")
						}

						override fun createDirectory(directory: List<String>) {
							println(">>> createDirectory '${directory.joinToString()}()'")
						}

						override fun editDirectoryName(rootDirectory: List<String>, newDirName: String) {
							println(">>> editDirectoryName '${rootDirectory.joinToString()}', '$newDirName', ")
						}

						override fun editFileName(directory: List<String>, oldFileName: String, newFileName: String) {
							println(">>> editFileName '${directory.joinToString()}', '$oldFileName', '$newFileName'")
						}

						override fun editFileContent(directory: List<String>, fileName: String, edit: (String) -> String?) {
							println(">>> editFileContent '$directory', '${directory.joinToString()}', '$fileName'")
						}

						override fun deleteFile(directory: List<String>, fileName: String) {
							println(">>> deleteFile '$directory', '${directory.joinToString()}', '$fileName'")
						}

						override fun deleteDirectory(directory: List<String>) {
							println(">>> deleteDirectory '${directory.joinToString()}',")
						}
					}
				}
			}
			actor.runAction()
			this.dispose()
			JPanel()
		}
	}
}