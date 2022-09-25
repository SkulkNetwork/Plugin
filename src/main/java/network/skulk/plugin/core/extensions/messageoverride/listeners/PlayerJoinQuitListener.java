package network.skulk.plugin.core.extensions.messageoverride.listeners;

import network.skulk.plugin.core.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.plugin.helpers.MiniMessageHelper;
import network.skulk.plugin.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerJoinQuitListener extends BaseListener<MessageOverrideExtension> {
    public PlayerJoinQuitListener(final @NotNull MessageOverrideExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final @NotNull PlayerJoinEvent event) {
        event.joinMessage(MiniMessageHelper.makeMessage("green", '+', "<0>", event.getPlayer().getName()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(final @NotNull PlayerQuitEvent event) {
        event.quitMessage(MiniMessageHelper.makeMessage("red", '-', "<0>", event.getPlayer().getName()));
    }
}
