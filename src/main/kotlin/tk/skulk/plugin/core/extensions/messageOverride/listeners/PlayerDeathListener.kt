package tk.skulk.plugin.core.extensions.messageOverride.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.PlayerDeathEvent
import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.messageOverride.MessageOverrideExtension
import tk.skulk.plugin.framework.Listener
import tk.skulk.plugin.util.makeMessageWithComponents

class PlayerDeathListener(extension: MessageOverrideExtension) : Listener<SNPlugin, MessageOverrideExtension>(
    extension
) {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onPlayerDeath(event: PlayerDeathEvent) {
        event.deathMessage(makeMessageWithComponents("red", '☠', "<0>.", event.deathMessage()!!))
    }
}
