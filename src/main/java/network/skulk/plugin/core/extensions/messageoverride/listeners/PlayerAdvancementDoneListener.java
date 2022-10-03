package network.skulk.plugin.core.extensions.messageoverride.listeners;

import network.skulk.plugin.core.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.plugin.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.jetbrains.annotations.NotNull;

import static network.skulk.plugin.helpers.MiniMessageHelper.makeMessageWithComponent;


// FIXME
public final class PlayerAdvancementDoneListener extends BaseListener<MessageOverrideExtension> {
    public PlayerAdvancementDoneListener(final @NotNull MessageOverrideExtension extension) {
        super(extension);
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerAdvancementDone(final @NotNull PlayerAdvancementDoneEvent event) {
        event.message(makeMessageWithComponent("blue", '✓', "<0>.", event.message()));
    }
}
