package tk.skulk.plugin.core.extensions.silenceMobs

import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.silenceMobs.listeners.PlayerInteractEntityListener
import tk.skulk.plugin.framework.Extension

class SilenceMobsExtension(plugin: SNPlugin) : Extension<SNPlugin>(plugin) {
    override val listeners = listOf {
        PlayerInteractEntityListener(this)
    }
}
