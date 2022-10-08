package tk.skulk.plugin.core.extensions.foodbehaviour.listeners;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.extensions.foodbehaviour.FoodBehaviourExtension;
import tk.skulk.plugin.wrapper.BaseListener;

import static tk.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

public final class CookieResetInsomniaListener extends BaseListener<FoodBehaviourExtension> {
    public CookieResetInsomniaListener(final @NotNull FoodBehaviourExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerItemConsume(final @NotNull PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.COOKIE) {
            return;
        }

        final var player = event.getPlayer();
        player.setStatistic(Statistic.TIME_SINCE_REST, 0);

        sendMessage(player, "gold", '✓', "Your insomnia has been reset.");
    }
}
