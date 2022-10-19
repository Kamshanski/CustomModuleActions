package dev.itssho.module.feature.preferences

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class ModuleCreatorSettingsComponent {

	// Components
	private val useQpayNameStepCheckBox = JBCheckBox("Use Qpay module mame step?")
	private val scriptPathInput = TextFieldWithBrowseButton()	// Пример https://github.com/JetBrains/intellij-community/blob/master/platform/platform-impl/src/com/intellij/ide/browsers/BrowserSettingsPanel.kt

	// Components accessors
	var useQpayNameStep: Boolean
		get() = useQpayNameStepCheckBox.isSelected
		set(newStatus) {
			useQpayNameStepCheckBox.isSelected = newStatus
		}

	var scriptPath: String
		get() = scriptPathInput.text
		set(value) { scriptPathInput.text = value }

	private fun makeModuleCreatorSettingsPanel(): JPanel {
		scriptPathInput.addBrowseFolderListener("Select path to Kotlin Script", null, null, FileChooserDescriptorFactory.createSingleFileDescriptor("kts"))
		return FormBuilder.createFormBuilder()
			.addLabeledComponent(JBLabel("Script path"), scriptPathInput, 1, false)
			.addComponent(useQpayNameStepCheckBox, 1)
			.addComponentFillVertically(JPanel(), 0)
			.panel
	}

	/*
	 Default properties
	 */

	// First panel to be focused
	val preferredFocusedComponent: JComponent
		get() = useQpayNameStepCheckBox

	// Root panel. Lazy as it requires other fields to be initialized firstly
	val panel: JPanel by lazy { makeModuleCreatorSettingsPanel() }
}