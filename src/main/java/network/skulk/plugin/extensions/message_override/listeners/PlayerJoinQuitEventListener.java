package network.skulk.plugin.extensions.message_override.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerJoinQuitEventListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(@NotNull final PlayerJoinEvent event) {
        final Component newMessage = Component.text("[ ", NamedTextColor.GRAY, TextDecoration.BOLD)
                .append(Component.text("+", NamedTextColor.GREEN, TextDecoration.BOLD))
                .append(Component.text(" ] ", NamedTextColor.GRAY, TextDecoration.BOLD))
                .append(Component.text(event.getPlayer().getName(), NamedTextColor.GREEN).decoration(TextDecoration.BOLD, false));

        event.joinMessage(newMessage);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(@NotNull final PlayerQuitEvent event) {
        final Component newMessage = Component.text("[ ", NamedTextColor.GRAY, TextDecoration.BOLD)
                .append(Component.text("-", NamedTextColor.RED, TextDecoration.BOLD))
                .append(Component.text(" ] ", NamedTextColor.GRAY, TextDecoration.BOLD))
                .append(Component.text(event.getPlayer().getName(), NamedTextColor.RED).decoration(TextDecoration.BOLD, false));

        event.quitMessage(newMessage);
    }
}
