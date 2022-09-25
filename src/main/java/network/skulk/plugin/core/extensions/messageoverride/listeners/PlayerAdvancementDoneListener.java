package network.skulk.plugin.core.extensions.messageoverride.listeners;

import network.skulk.plugin.core.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.plugin.helpers.MiniMessageHelper;
import network.skulk.plugin.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;


public final class PlayerAdvancementDoneListener extends BaseListener<MessageOverrideExtension> {
    public PlayerAdvancementDoneListener(final MessageOverrideExtension extension) {
        super(extension);
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    void onPlayerAdvancementDone(final PlayerAdvancementDoneEvent event) {
        event.message(MiniMessageHelper.makeMessageWithComponent("blue", '✓', "<0>.", event.message()));
    }
}
