package tk.skulk.plugin.core.extensions.messageOverride

import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.messageOverride.listeners.PlayerDeathListener
import tk.skulk.plugin.core.extensions.messageOverride.listeners.PlayerJoinQuitListener
import tk.skulk.plugin.core.extensions.messageOverride.listeners.UnknownCommandListener
import tk.skulk.plugin.framework.Extension

class MessageOverrideExtension(plugin: SNPlugin) : Extension<SNPlugin>(plugin) {
    override val listeners = listOf(
        { PlayerDeathListener(this) },
        { PlayerJoinQuitListener(this) },
        { UnknownCommandListener(this) },
    )
}
