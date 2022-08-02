package network.skulk.plugin.extensions.message_override.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.command.UnknownCommandEvent;
import org.jetbrains.annotations.NotNull;

public final class UnknownCommandListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onUnknownCommand(@NotNull UnknownCommandEvent event) {
        Component newMessage = Component.text("[ ", NamedTextColor.GRAY, TextDecoration.BOLD)
                .append(Component.text("!", NamedTextColor.RED, TextDecoration.BOLD))
                .append(Component.text(" ] ", NamedTextColor.GRAY, TextDecoration.BOLD))
                .append(Component.text("Unknown command: ").color(NamedTextColor.RED).decoration(TextDecoration.BOLD, false))
                .append(Component.text(event.getCommandLine(), NamedTextColor.RED, TextDecoration.BOLD));

        event.message(newMessage);
    }
}
