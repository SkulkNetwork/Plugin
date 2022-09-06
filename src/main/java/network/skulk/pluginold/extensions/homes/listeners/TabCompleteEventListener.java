package network.skulk.pluginold.extensions.homes.listeners;

import network.skulk.pluginold.Plugin;
import network.skulk.pluginold.extensions.homes.HomesExtension;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.HashSet;

public record TabCompleteEventListener(HomesExtension extension) implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onTabComplete(final TabCompleteEvent event) {
        final String buffer = event.getBuffer();
        if (event.isCommand() && (buffer.equalsIgnoreCase("/home-delete") || buffer.equalsIgnoreCase("/home"))) {
            final CommandSender sender = event.getSender();

            if (!(sender instanceof Player player)) {
                return;
            }

            final HashSet<String> playerHomes = player.getPersistentDataContainer().getOrDefault(extension.HOMES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, new HashSet<>());

            final ArrayList<String> completions = new ArrayList<>();

            for (final String homeString : playerHomes) {
                completions.add(homeString.split(" ")[0]);
            }

            event.setCompletions(completions);
        }
    }
}
