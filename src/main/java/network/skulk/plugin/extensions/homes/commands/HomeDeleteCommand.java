package network.skulk.plugin.extensions.homes.commands;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.constants.Message;
import network.skulk.plugin.extensions.homes.HomesExtension;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public final class HomeDeleteCommand implements CommandExecutor {
    private final @NotNull HomesExtension extension;

    public HomeDeleteCommand(@NotNull HomesExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "home-delete");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ONLY_PLAYERS_CAN_USE_THIS_COMMAND);
            return true;
        }

        if (args.length > 1) {
            return false;
        }

        String homeName;

        if (args.length == 0) {
            homeName = "home";
        } else {
            homeName = args[0].toLowerCase();
        }

        PersistentDataContainer playerContainer = player.getPersistentDataContainer();
        HashSet<String> playerHomes = playerContainer.getOrDefault(extension.HOMES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, new HashSet<>());

        boolean homeExists = false;

        for (String homeString : playerHomes) {
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