package network.skulk.plugin.extensions.homes.commands;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.constants.Message;
import network.skulk.plugin.extensions.homes.HomesExtension;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public final class HomeListCommand implements CommandExecutor {
    private final @NotNull HomesExtension extension;

    public HomeListCommand(@NotNull HomesExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "home-list");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ONLY_PLAYERS_CAN_USE_THIS_COMMAND);
            return true;
        }

        if (args.length != 0) {
            return false;
        }

        HashSet<String> playerHomes = player.getPersistentDataContainer().getOrDefault(extension.HOMES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, new HashSet<>());

        if (playerHomes.size() == 0) {
            player.sendRichMessage(Message.NO_HOMES);
            return true;
        }

        StringBuilder response = new StringBuilder(Message.HOMES);

        for (String homeString : playerHomes) {
            String[] d = homeString.split(" ");

            String name = d[0];
            double x = Double.parseDouble(d[2]);
            double y = Double.parseDouble(d[3]);
            double z = Double.parseDouble(d[4]);

            response.append(Message.HOME.formatted(name, x, y, z));
        }

        player.sendRichMessage(response.toString());

        return true;
    }
}