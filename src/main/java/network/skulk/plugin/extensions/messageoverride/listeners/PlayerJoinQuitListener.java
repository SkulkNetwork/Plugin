package network.skulk.plugin.extensions.messageoverride.listeners;

import network.skulk.plugin.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import static network.skulk.helpers.MiniMessageHelper.makeMessage;

public final class PlayerJoinQuitListener extends BaseListener<MessageOverrideExtension> {
    public PlayerJoinQuitListener(final @NotNull MessageOverrideExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final @NotNull PlayerJoinEvent event) {
        event.joinMessage(makeMessage("green", '+', "<0>", event.getPlayer().getName()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(final @NotNull PlayerQuitEvent event) {
        event.quitMessage(makeMessage("red", '-', "<0>", event.getPlayer().getName()));
    }
}
