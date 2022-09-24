package network.skulk.plugin.extensions.messageoverride.listeners;

import network.skulk.plugin.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import static network.skulk.helpers.MiniMessageHelper.makeMessageWithComponent;


public final class PlayerAdvancementDoneListener extends BaseListener<MessageOverrideExtension> {
    public PlayerAdvancementDoneListener(final MessageOverrideExtension extension) {
        super(extension);
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    void onPlayerAdvancementDone(final PlayerAdvancementDoneEvent event) {
        event.message(makeMessageWithComponent("blue", '✓', "<0>.", event.message()));
    }
}
