package tk.skulk.plugin.core.extensions.messageOverride.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.messageOverride.MessageOverrideExtension
import tk.skulk.plugin.framework.Listener
import tk.skulk.plugin.util.makeMessageWithComponents

// FIXME: Doesn't work for some reason.
class PlayerAdvancementDoneListener(extension: MessageOverrideExtension) : Listener<SNPlugin, MessageOverrideExtension>(
    extension
) {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onPlayerAdvancementDone(event: PlayerAdvancementDoneEvent) {
        event.message(makeMessageWithComponents("blue", '✓', "<0>.", event.message()!!))
    }
}
