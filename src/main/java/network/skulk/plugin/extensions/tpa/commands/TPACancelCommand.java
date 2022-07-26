package network.skulk.plugin.extensions.tpa.commands;

import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.plugin.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public final class TPACancelCommand implements CommandExecutor {
    private final @NotNull TPAExtension extension;

    public TPACancelCommand(@NotNull TPAExtension tpaExtension) {
        extension = tpaExtension;
        extension.register("tpa-cancel", this);
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

        Player target;
        String targetName;

        if (args.length == 1)
            targetName = args[0];
        else {
            ArrayList<String> playerOutGoingRequests = new ArrayList<>();

            for (Map.Entry<String, HashSet<String>> entry : extension.tpaRequests.entrySet()) {
                // Key is the target, the player will be in the value.
                if (entry.getValue().contains(playerName)) {
                    playerOutGoingRequests.add(entry.getKey());
                }
            }

            int playerOutGoingRequestsSize = playerOutGoingRequests.size();

            if (playerOutGoingRequestsSize == 0) {
                player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> <red>You don't have any outgoing TPA requests.</red>");
                return true;
            } else if (playerOutGoingRequestsSize == 1) {
                targetName = playerOutGoingRequests.get(0);
            } else {
                StringBuilder response = new StringBuilder("<bold><gray>[ <blue>?</blue> ]</gray></bold> <blue>Seems like you have multiple incoming TPA requests. Which one would you like to cancel?</blue>");

                for (String toCancel : playerOutGoingRequests) {
                    response.append("\n<bold><gray>-></grey> <color:#ffae1a><click:run_command:/tpa-cancel %s>[%s]</click></color></bold>".formatted(toCancel, toCancel));
                }

                player.sendRichMessage(response.toString());
                return true;
            }
        }

        HashSet<String> targetIncomingRequests = extension.tpaRequests.computeIfAbsent(targetName, k -> new HashSet<>());

        if (!targetIncomingRequests.contains(playerName)) {
            player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> <red>You don't have an outgoing TPA request to <bold>%s</bold>.</red>".formatted(targetName));
            return true;
        }

        target = Bukkit.getPlayer(targetName);

        player.sendRichMessage("<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Rejected <bold>%s</bold>'s TPA request.</green>".formatted(targetName));

        if (target != null && target.isOnline()) {
            target.sendRichMessage("<bold><gray>[ <color:#ffae1a>!</color:#ffae1a> ]</gray> <color:#ffae1a>%s</bold> has cancelled their TPA request to you.</color>".formatted(playerName));
        }

        return true;
    }
}
