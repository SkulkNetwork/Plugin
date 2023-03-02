package tk.skulk.plugin.core.extensions.entityOverride

import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.entityOverride.listeners.CreeperNoGriefListener
import tk.skulk.plugin.core.extensions.entityOverride.listeners.DragonDropElytraListener
import tk.skulk.plugin.core.extensions.entityOverride.listeners.EndermanNoGriefListener
import tk.skulk.plugin.core.extensions.entityOverride.listeners.ExplosionDropAllListener
import tk.skulk.plugin.framework.Extension

class EntityOverrideExtension(plugin: SNPlugin) : Extension<SNPlugin>(plugin) {
    override val listeners = listOf(
        { CreeperNoGriefListener(this) },
        { DragonDropElytraListener(this) },
        { EndermanNoGriefListener(this) },
        { ExplosionDropAllListener(this) },
    )
}
