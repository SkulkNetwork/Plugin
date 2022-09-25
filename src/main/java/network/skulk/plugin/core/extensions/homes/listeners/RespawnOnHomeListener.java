package network.skulk.plugin.core.extensions.homes.listeners;

import network.skulk.plugin.core.extensions.homes.HomesExtension;
import network.skulk.plugin.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;

public final class RespawnOnHomeListener extends BaseListener<HomesExtension> {
    public RespawnOnHomeListener(final @NotNull HomesExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(final @NotNull PlayerRespawnEvent event) {
        final var playerHomes = this.getExtension().getHomes().get(event.getPlayer().getUniqueId());

        if (playerHomes.isEmpty()) {
            return;
        }

        if (playerHomes.size() == 1) {
            event.setRespawnLocation(playerHomes.firstEntry().getValue().toLocation());
            return;
        }

        final var defaultHome = playerHomes.get("home");

        if (defaultHome != null) {
            event.setRespawnLocation(defaultHome.toLocation());
        }
    }
}
