package network.skulk.pluginrewrite.extensions.messageoverride.listeners;

import network.skulk.pluginrewrite.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import static network.skulk.utils.MiniMessageFormat.mmWithComponent;

public final class PlayerDeathListener extends BaseListener<MessageOverrideExtension> {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDeath(@NotNull final PlayerDeathEvent event) {
        event.deathMessage(mmWithComponent("<bold><gray>[ <red>☠</red> ]</gray></bold><red><0>.</red>", event.deathMessage()));
    }
}
