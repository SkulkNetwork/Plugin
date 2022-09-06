package network.skulk.pluginold.extensions.homes.commands;

import network.skulk.pluginold.Plugin;
import network.skulk.pluginold.constants.Message;
import network.skulk.pluginold.extensions.homes.HomesExtension;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public final class HomeDeleteCommand implements CommandExecutor {
    private final @NotNull HomesExtension extension;

    public HomeDeleteCommand(@NotNull final HomesExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "home-delete");
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

        boolean homeExists = false;

        for (final String homeString : playerHomes) {
            if (homeString.split(" ")[0].equals(homeName)) {
                playerHomes.remove(homeString);
                homeExists = true;
                break;
            }
        }

        if (!homeExists) {
            player.sendRichMessage(Message.NO_SUCH_HOME.formatted(homeName));
            return true;
        }

        playerContainer.set(extension.HOMES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, playerHomes);
        player.sendRichMessage(Message.HOME_DELETED.formatted(homeName));

        return true;
    }
}