package network.skulk.plugin.extensions.homes.listeners;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.extensions.homes.HomesExtension;
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
    public void onTabComplete(TabCompleteEvent event) {
        String buffer = event.getBuffer();
        if (event.isCommand() && (buffer.equalsIgnoreCase("/home-delete") || buffer.equalsIgnoreCase("/home"))) {
            CommandSender sender = event.getSender();

            if (!(sender instanceof Player player)) {
                return;
            }

            HashSet<String> playerHomes = player.getPersistentDataContainer().getOrDefault(extension.HOMES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, new HashSet<>());

            ArrayList<String> completions = new ArrayList<>();

            for (String homeString : playerHomes) {
                completions.add(homeString.split(" ")[0]);
            }

            event.setCompletions(completions);
        }
    }
}
