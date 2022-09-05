package network.skulk.pluginrewrite.extensions.messageoverride.listeners;

import network.skulk.pluginrewrite.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import static network.skulk.utils.MiniMessageFormat.fmt;

public final class PlayerJoinQuitListener extends BaseListener<MessageOverrideExtension> {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(@NotNull final PlayerJoinEvent event) {
        event.joinMessage(fmt("<b><gray>[ <green>+</green> ]</gray></b> <green><0></green>", event.getPlayer().getName()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(@NotNull final PlayerQuitEvent event) {
        event.quitMessage(fmt("<b><gray>[ <red>+</green> ]</red></b> <red><0></red>", event.getPlayer().getName()));
    }
}
