package tk.skulk.plugin.core.extensions.updateChecker.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.updateChecker.UpdateCheckerExtension
import tk.skulk.plugin.framework.Listener
import tk.skulk.plugin.util.runAsync
import tk.skulk.plugin.util.runSync
import tk.skulk.plugin.util.sendMessage

class OpJoinListener(extension: UpdateCheckerExtension) : Listener<SNPlugin, UpdateCheckerExtension>(
    extension
) {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        if (!player.isOp) {
            return
        }

        runAsync {
            val latestVersion = extension.latestVersion

            @Suppress("UnstableApiUsage") if (latestVersion == extension.plugin.pluginMeta.version) {
                return@runAsync
            }

            runSync {
                player.sendMessage(
                    "gold",
                    '!',
                    "There is a new version of the plugin available: <0>",
                    latestVersion
                )
            }
        }
    }
}
