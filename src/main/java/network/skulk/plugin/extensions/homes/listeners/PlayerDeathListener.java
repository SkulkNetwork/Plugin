package network.skulk.plugin.extensions.homes.listeners;

import network.skulk.plugin.extensions.homes.Home;
import network.skulk.plugin.extensions.homes.HomesExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerRespawnEvent;

public final class PlayerDeathListener extends BaseListener<HomesExtension> {
    public PlayerDeathListener(final HomesExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(final PlayerRespawnEvent event) {
        final var playerHomes = this.getExtension().getHomes().get(event.getPlayer().getName());

        if (playerHomes.isEmpty()) {
            return;
        }

        if (playerHomes.size() == 1) {
            event.setRespawnLocation(playerHomes.iterator().next().location());
            return;
        }

        for (final Home home : playerHomes) {
            if (home.name().equalsIgnoreCase("home")) {
                event.setRespawnLocation(home.location());
            }
        }
    }
}
