package tk.skulk.plugin.core.extensions.entityOverride.listeners

import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityChangeBlockEvent
import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.entityOverride.EntityOverrideExtension
import tk.skulk.plugin.framework.Listener

class EndermanNoGriefListener(extension: EntityOverrideExtension) : Listener<SNPlugin, EntityOverrideExtension>(
    extension
) {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onEntityChangeBlock(event: EntityChangeBlockEvent) {
        if (event.entityType != EntityType.ENDERMAN) {
            return
        }

        event.isCancelled = true
    }
}
