package dev.itssho.module.feature.preferences

import com.intellij.openapi.options.Configurable
import dev.itssho.module.shared.preferences.ModuleCreatorSettingsState
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

/**
 * Provides controller functionality for application settings.
 */
class ModuleCreatorSettingConfigurable : Configurable {

	private var _moduleCreatorSettingsComponent: ModuleCreatorSettingsComponent? = null
	private val component: ModuleCreatorSettingsComponent get() = _moduleCreatorSettingsComponent!!

	// A default constructor with no arguments is required because this implementation
	// is registered as an applicationConfigurable EP
	@Nls(capitalization = Nls.Capitalization.Title)
	override fun getDisplayName(): String =
		"SDK: Application Settings Example"

	override fun getPreferredFocusedComponent(): JComponent =
		component.preferredFocusedComponent

	override fun createComponent(): JComponent {
		_moduleCreatorSettingsComponent = ModuleCreatorSettingsComponent()
		return component.panel
	}

	override fun isModified(): Boolean = runWithSettings { s ->
		component.run {
			   s.useQpayNameStep 	!= useQpayNameStep
			|| s.scriptPath 		!= scriptPath
		}
	}

	override fun apply() = runWithSettings { s ->
		component.run {
			s.scriptPath 		= scriptPath
			s.useQpayNameStep 	= useQpayNameStep
		}
	}

	override fun reset() = runWithSettings { s ->
		component.run {
			scriptPath 			= s.scriptPath
			useQpayNameStep 	= s.useQpayNameStep
		}
	}

	override fun disposeUIResources() {
		_moduleCreatorSettingsComponent = null
	}

	private inline fun <T> runWithSettings(block: (settings: ModuleCreatorSettingsState) -> T): T {
		val settings = ModuleCreatorSettingsState.getInstance().state
		return block(settings)
	}
}