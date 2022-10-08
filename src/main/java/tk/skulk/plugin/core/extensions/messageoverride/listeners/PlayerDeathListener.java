package tk.skulk.plugin.core.extensions.messageoverride.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.extensions.messageoverride.MessageOverrideExtension;
import tk.skulk.plugin.wrapper.BaseListener;

import static tk.skulk.plugin.helpers.MiniMessageHelper.makeMessageWithComponent;

public final class PlayerDeathListener extends BaseListener<MessageOverrideExtension> {
    public PlayerDeathListener(final @NotNull MessageOverrideExtension extension) {
        super(extension);
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDeath(final @NotNull PlayerDeathEvent event) {
        event.deathMessage(makeMessageWithComponent("red", '☠', "<0>.", event.deathMessage()));
    }
}
