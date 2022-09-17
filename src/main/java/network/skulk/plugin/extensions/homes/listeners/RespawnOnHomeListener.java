package network.skulk.plugin.extensions.homes.listeners;

import network.skulk.plugin.extensions.homes.HomesExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerRespawnEvent;

public final class RespawnOnHomeListener extends BaseListener<HomesExtension> {
    public RespawnOnHomeListener(final HomesExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(final PlayerRespawnEvent event) {
        final var playerHomes = this.getExtension().getHomes().get(event.getPlayer().getUniqueId());

        if (playerHomes.isEmpty()) {
            return;
        }

        if (playerHomes.size() == 1) {
            event.setRespawnLocation(playerHomes.firstEntry().getValue());
            return;
        }

        final var defaultHome = playerHomes.get("home");
        if (defaultHome != null) {
            event.setRespawnLocation(defaultHome);
        }
    }
}
