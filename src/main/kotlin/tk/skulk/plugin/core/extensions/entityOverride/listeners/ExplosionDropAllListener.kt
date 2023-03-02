package tk.skulk.plugin.core.extensions.entityOverride.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityExplodeEvent
import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.entityOverride.EntityOverrideExtension
import tk.skulk.plugin.framework.Listener

class ExplosionDropAllListener(extension: EntityOverrideExtension) : Listener<SNPlugin, EntityOverrideExtension>(
    extension
) {
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onEntityExplode(event: EntityExplodeEvent) {
        event.yield = 100F
    }
}
