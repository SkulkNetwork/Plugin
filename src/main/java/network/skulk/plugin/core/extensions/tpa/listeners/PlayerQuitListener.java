package network.skulk.plugin.core.extensions.tpa.listeners;

import network.skulk.plugin.core.extensions.tpa.TPAExtension;
import network.skulk.plugin.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public final class PlayerQuitListener extends BaseListener<TPAExtension> {
    public PlayerQuitListener(final @NotNull TPAExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(final @NotNull PlayerQuitEvent event) {
        final var player = event.getPlayer();
        final var tpaRequests = this.getExtension().getTpaRequests();

        for (final BukkitTask cancelTask : tpaRequests.get(player).values()) {
            cancelTask.cancel();
        }

        tpaRequests.remove(player);
    }
}
