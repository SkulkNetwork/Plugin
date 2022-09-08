package network.skulk.plugin.extensions.messageoverride.listeners;

import network.skulk.plugin.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

import static network.skulk.utils.MiniMessageFormat.makeMessageWithComponent;

public final class PlayerDeathListener extends BaseListener<MessageOverrideExtension> {
    public PlayerDeathListener(final MessageOverrideExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        event.deathMessage(makeMessageWithComponent("red", '☠', "<0>.", event.deathMessage()));
    }
}
