package tk.skulk.plugin.core.extensions.silenceMobs.listeners

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerInteractEntityEvent
import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.silenceMobs.SilenceMobsExtension
import tk.skulk.plugin.framework.Listener
import tk.skulk.plugin.util.asString
import tk.skulk.plugin.util.format
import tk.skulk.plugin.util.sendMessage

class PlayerInteractEntityListener(extension: SilenceMobsExtension) : Listener<SNPlugin, SilenceMobsExtension>(
    extension
) {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        val player = event.player

        val nameTags = when {
            player.inventory.itemInMainHand.type == Material.NAME_TAG -> player.inventory.itemInMainHand
            player.inventory.itemInOffHand.type == Material.NAME_TAG -> player.inventory.itemInOffHand
            else -> return
        }

        if (nameTags.displayName().asString().equals("[Silenced]", ignoreCase = true)) {
            return
        }

        val entity = event.rightClicked

        if (entity.type == EntityType.UNKNOWN) {
            return
        }

        entity.isSilent = true
        entity.customName(format("<i><gray>Silenced</gray></i>"))

        if (player.gameMode != GameMode.CREATIVE) {
            nameTags.amount -= 1
        }

        player.sendMessage(
            "green", '✓', "The entity '<0>' has been silenced.", entity.type.toString().lowercase()
        )

        event.isCancelled = true
    }
}
