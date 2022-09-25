package network.skulk.plugin.core.extensions.foodbehaviour.listeners;

import network.skulk.plugin.core.extensions.foodbehaviour.FoodBehaviourExtension;
import network.skulk.plugin.helpers.MiniMessageHelper;
import network.skulk.plugin.wrapper.BaseListener;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public final class CookieResetInsomniaListener extends BaseListener<FoodBehaviourExtension> {
    public CookieResetInsomniaListener(final FoodBehaviourExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerItemConsume(final PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.COOKIE) {
            return;
        }

        final var player = event.getPlayer();
        player.setStatistic(Statistic.TIME_SINCE_REST, 0);

        MiniMessageHelper.sendMessage(player, "gold", '✓', "Your insomnia has been reset.");
    }
}
