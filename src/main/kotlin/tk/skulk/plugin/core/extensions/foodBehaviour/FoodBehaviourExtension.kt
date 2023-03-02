package tk.skulk.plugin.core.extensions.foodBehaviour

import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.foodBehaviour.listeners.CookieResetInsomniaListener
import tk.skulk.plugin.framework.Extension

class FoodBehaviourExtension(plugin: SNPlugin) : Extension<SNPlugin>(plugin) {
    override val listeners = listOf {
        CookieResetInsomniaListener(this)
    }
}
