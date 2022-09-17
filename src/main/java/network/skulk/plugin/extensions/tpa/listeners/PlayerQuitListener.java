package network.skulk.plugin.extensions.tpa.listeners;

import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerQuitListener extends BaseListener<TPAExtension> {
    public PlayerQuitListener(final TPAExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final var playerName = event.getPlayer().getName();
        final var playerIncomingRequests = this.getExtension().getTpaRequests().get(playerName);

        playerIncomingRequests.get(playerName).cancel();
        playerIncomingRequests.remove(playerName);
    }
}
