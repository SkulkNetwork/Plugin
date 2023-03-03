package tk.skulk.plugin.core.extensions.foodBehaviour.listeners

import org.bukkit.Material
import org.bukkit.Statistic
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerItemConsumeEvent
import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.foodBehaviour.FoodBehaviourExtension
import tk.skulk.plugin.framework.Listener
import tk.skulk.plugin.util.sendMessage

class CookieResetInsomniaListener(extension: FoodBehaviourExtension) : Listener<SNPlugin, FoodBehaviourExtension>(
    extension
) {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerItemConsume(event: PlayerItemConsumeEvent) {
        if (event.item.type != Material.COOKIE) {
            return
        }

        val player = event.player

        if (player.getStatistic(Statistic.TIME_SINCE_REST) < 100) {
            return
        }

        event.player.setStatistic(Statistic.TIME_SINCE_REST, 0)

        event.player.sendMessage("green", '✓', "Your insomnia has been reset.")
    }
}
