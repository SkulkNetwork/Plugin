package network.skulk.plugin.extensions.homes.commands;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.constants.Message;
import network.skulk.plugin.extensions.homes.HomesExtension;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public final class HomeAddCommand implements CommandExecutor {
    private final @NotNull HomesExtension extension;

    public HomeAddCommand(@NotNull HomesExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "home-add");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ONLY_PLAYERS_CAN_USE_THIS_COMMAND);
            return true;
        }

        if (args.length != 1) {
            return false;
        }

        String homeName = args[0].toLowerCase();

        PersistentDataContainer playerContainer = player.getPersistentDataContainer();
        HashSet<String> playerHomes = playerContainer.getOrDefault(extension.HOMES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, new HashSet<>());

        for (String homeString : playerHomes) {
            // There is no spaces in the name, so this is fine.
            if (homeString.startsWith(homeName)) {
                player.sendRichMessage(Message.HOME_ALREADY_EXISTS.formatted(homeName));
                return true;
            }
        }

        if (playerHomes.size() >= 16) {
            player.sendRichMessage(Message.HOME_LIMIT_REACHED);
            return true;
        }

        Location l = player.getLocation();
        double x = l.getX();
        double y = l.getY();
        double z = l.getZ();

        playerHomes.add("%s %s %f %f %f".formatted(homeName, player.getWorld().getName(), x, y, z));

        playerContainer.set(extension.HOMES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, playerHomes);

        player.sendRichMessage(Message.HOME_ADDED.formatted(homeName, x, y, z));

        return true;
    }
}
