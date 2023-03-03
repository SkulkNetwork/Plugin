package tk.skulk.plugin.core.extensions.messageOverride.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.command.UnknownCommandEvent
import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.messageOverride.MessageOverrideExtension
import tk.skulk.plugin.framework.Listener
import tk.skulk.plugin.util.makeMessage

class UnknownCommandListener(extension: MessageOverrideExtension) : Listener<SNPlugin, MessageOverrideExtension>(
    extension
) {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onUnknownCommand(event: UnknownCommandEvent) {
        event.message(
            makeMessage(
                "red", '!', "Unknown command: <0>", event.commandLine
            )
        )
    }
}
