package tk.skulk.plugin.core.extensions.messageoverride.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.extensions.messageoverride.MessageOverrideExtension;
import tk.skulk.plugin.wrapper.BaseListener;

import static tk.skulk.plugin.helpers.MiniMessageHelper.makeMessageWithComponent;


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
