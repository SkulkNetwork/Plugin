package tk.skulk.plugin.core.extensions.messageOverride.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.messageOverride.MessageOverrideExtension
import tk.skulk.plugin.framework.Listener
import tk.skulk.plugin.util.makeMessage

class PlayerJoinQuitListener(extension: MessageOverrideExtension) : Listener<SNPlugin, MessageOverrideExtension>(
    extension
) {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.joinMessage(makeMessage("green", '+', "<0>", event.player.name))
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.quitMessage(makeMessage("red", '-', "<0>", event.player.name))
    }
}
