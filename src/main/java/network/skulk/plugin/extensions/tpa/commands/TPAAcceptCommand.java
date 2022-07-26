package network.skulk.plugin.extensions.tpa.commands;

import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.plugin.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class TPAAcceptCommand implements CommandExecutor {
    private final @NotNull TPAExtension extension;

    public TPAAcceptCommand(@NotNull TPAExtension tpaExtension) {
        extension = tpaExtension;
        extension.register("tpa-accept", this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            Message.sendOnlyPlayer(sender);
            return true;
        } else if (args.length > 1) {
            return false;
        }

        String playerName = player.getName();
        HashSet<String> playerIncomingRequests = extension.tpaRequests.computeIfAbsent(playerName, k -> new HashSet<>());

        Player target;
        String targetName;

        if (args.length == 1) {
            targetName = args[0];
        } else {
            // No target was specified.
            if (playerIncomingRequests.size() == 0) {
                // The player has no incoming TPA requests.
                player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> <red>You don't have any incoming TPA requests.</red>");
                return true;
            } else if (playerIncomingRequests.size() == 1) {
                // The player has 1 incoming TPA request.
                targetName = playerIncomingRequests.iterator().next();
            } else {
                // Multiple people want to TPA to the player.
                StringBuilder response = new StringBuilder()
                        .append("<bold><gray>[ <blue>?</blue> ]</gray></bold> <blue>Seems like you have multiple people wanting to TPA to you. Which one would you like to accept?</blue>");

                for (String toAccept : playerIncomingRequests) {
                    response.append("\n<bold><green><click:run_command:/tpa-accept %s>[%s]</click></green></bold>".formatted(toAccept, toAccept));
                }

                player.sendRichMessage(response.toString());
                return true;
            }
        }

        target = Bukkit.getPlayer(targetName);

        if (target == null || !target.isOnline()) {
            Message.sendPlayerOffline(player);
            return true;
        } else if (!playerIncomingRequests.contains(targetName)) {
            player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray> <red>%s</bold> doesn't want to TPA to you.</red>".formatted(targetName));
            return true;
        }

        player.sendRichMessage("<bold><gray>[ <green>!</green> ]</gray></bold> <green>Teleporting <bold>%s</bold> to you...</green>".formatted(targetName));
        target.sendRichMessage("<bold><gray>[ <green>!</green> ]</gray></bold> <green>Teleporting you to <bold>%s</bold>...</green>".formatted(playerName));

        target.teleport(player);
        playerIncomingRequests.remove(targetName);

        return true;
    }
}
