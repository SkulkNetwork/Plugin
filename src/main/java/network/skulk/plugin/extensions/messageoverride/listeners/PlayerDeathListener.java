package network.skulk.plugin.extensions.messageoverride.listeners;

import network.skulk.plugin.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

import static network.skulk.utils.MiniMessageFormat.mmWithComponent;

public final class PlayerDeathListener extends BaseListener<MessageOverrideExtension> {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        event.deathMessage(mmWithComponent("<b><gray>[ <red>☠</red> ]</gray></b><red><0>.</red>", event.deathMessage()));
    }
}
