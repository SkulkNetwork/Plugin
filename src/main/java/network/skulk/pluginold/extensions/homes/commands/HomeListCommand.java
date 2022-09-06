package network.skulk.pluginold.extensions.homes.commands;

import network.skulk.pluginold.Plugin;
import network.skulk.pluginold.constants.Message;
import network.skulk.pluginold.extensions.homes.HomesExtension;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public final class HomeListCommand implements CommandExecutor {
    private final @NotNull HomesExtension extension;

    public HomeListCommand(@NotNull final HomesExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "home-list");
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ONLY_PLAYERS_CAN_USE_THIS_COMMAND);
            return true;
        }

        if (args.length != 0) {
            return false;
        }

        final HashSet<String> playerHomes = player.getPersistentDataContainer().getOrDefault(extension.HOMES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, new HashSet<>());

        if (playerHomes.size() == 0) {
            player.sendRichMessage(Message.NO_HOMES);
            return true;
        }

        final StringBuilder response = new StringBuilder(Message.HOMES);

        for (final String homeString : playerHomes) {
            final String[] d = homeString.split(" ");

            final String name = d[0];
            final double x = Double.parseDouble(d[2]);
            final double y = Double.parseDouble(d[3]);
            final double z = Double.parseDouble(d[4]);

            response.append(Message.HOME.formatted(name, x, y, z));
        }

        player.sendRichMessage(response.toString());

        return true;
    }
}