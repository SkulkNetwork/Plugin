package network.skulk.plugin.core.extensions.messageoverride.listeners;

import network.skulk.plugin.core.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.plugin.helpers.MiniMessageHelper;
import network.skulk.plugin.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerDeathListener extends BaseListener<MessageOverrideExtension> {
    public PlayerDeathListener(final @NotNull MessageOverrideExtension extension) {
        super(extension);
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDeath(final @NotNull PlayerDeathEvent event) {
        event.deathMessage(MiniMessageHelper.makeMessageWithComponent("red", '☠', "<0>.", event.deathMessage()));
    }
}
