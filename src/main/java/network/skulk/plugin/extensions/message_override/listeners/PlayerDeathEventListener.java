package network.skulk.plugin.extensions.message_override.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerDeathEventListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDeath(@NotNull PlayerDeathEvent event) {
        @SuppressWarnings("ConstantConditions") Component newMessage = Component.text("[ ", NamedTextColor.GRAY, TextDecoration.BOLD)
                .append(Component.text("☠", NamedTextColor.RED, TextDecoration.BOLD))
                .append(Component.text(" ] ", NamedTextColor.GRAY, TextDecoration.BOLD))
                .append(event.deathMessage().color(NamedTextColor.RED).decoration(TextDecoration.BOLD, false))
                .append(Component.text(".").decoration(TextDecoration.BOLD, false));

        event.deathMessage(newMessage);
    }
}
