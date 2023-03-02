package tk.skulk.plugin.oldcore.extensions.homes.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.oldcore.extensions.homes.HomesExtension;
import tk.skulk.plugin.utils.Location;
import tk.skulk.plugin.wrapper.BaseListener;

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

        final Location defaultHome;

        if (playerHomes.size() == 1) {
            defaultHome = playerHomes.firstEntry().getValue();
        }
        else {
            defaultHome = playerHomes.get("default");

            if (defaultHome == null) {
                return;
            }
        }

        event.setRespawnLocation(defaultHome);
    }
}
