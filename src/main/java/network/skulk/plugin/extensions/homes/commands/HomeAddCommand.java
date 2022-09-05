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

    public HomeAddCommand(@NotNull final HomesExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "home-add");
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ONLY_PLAYERS_CAN_USE_THIS_COMMAND);
            return true;
        }

        if (args.length > 1) {
            return false;
        }

        final String homeName;

        if (args.length == 0) {
            homeName = "home";
        } else {
            homeName = args[0].toLowerCase();
        }

        final PersistentDataContainer playerContainer = player.getPersistentDataContainer();
        final HashSet<String> playerHomes = playerContainer.getOrDefault(extension.HOMES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, new HashSet<>());

        for (final String homeString : playerHomes) {
            if (homeString.split(" ")[0].equals(homeName)) {
                player.sendRichMessage(Message.HOME_ALREADY_EXISTS.formatted(homeName));
                return true;
            }
        }

        if (playerHomes.size() >= 16) {
            player.sendRichMessage(Message.HOME_LIMIT_REACHED);
            return true;
        }

        final Location l = player.getLocation();
        final double x = l.getX();
        final double y = l.getY();
        final double z = l.getZ();
        final float yaw = l.getYaw();
        final float pitch = l.getPitch();

        playerHomes.add("%s %s %f %f %f %f %f".formatted(homeName, player.getWorld().getName(), x, y, z, yaw, pitch));

        playerContainer.set(extension.HOMES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, playerHomes);

        player.sendRichMessage(Message.HOME_ADDED.formatted(homeName, x, y, z));

        return true;
    }
}
