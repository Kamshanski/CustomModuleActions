package dev.itssho.module.service.preferences.data.persistent

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
	name = "dev.itssho.module.service.preferences.data.persistent.ModuleCreatorSettingsState",
	storages = [Storage("ItsshoModuleCreatorPluginSetting.xml")]
)
class ModuleCreatorSettingsState : PersistentStateComponent<ModuleCreatorSettingsState>, Settings {
	private val map: MutableMap<String, String> = HashMap()

	// Transient нужен, чтобы создаваемая котлином переменная useQpayNameStep$delegate: MutableMap<String, String> НЕ попала в сериализацию XML
	// Хотя мб сериализатор умный, не тронул бы эту переменную. Или мб в Class<> не будет этого поля. Но хз, пусть будет Transient
	@delegate:Transient override var useQpayNameStep	 by boolean(false,	map)
	@delegate:Transient override var scriptPath			 by string ("",		map)



	override fun getState(): ModuleCreatorSettingsState {
		return this
	}

	override fun loadState(state: ModuleCreatorSettingsState) {
		XmlSerializerUtil.copyBean(state, this)
	}

	companion object {

		fun getInstance(): ModuleCreatorSettingsState {
			val app = ApplicationManager.getApplication()
			return app.getService(ModuleCreatorSettingsState::class.java)
		}
	}
}
