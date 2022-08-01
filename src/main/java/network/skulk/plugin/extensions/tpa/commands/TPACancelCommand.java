package network.skulk.plugin.extensions.tpa.commands;

import network.skulk.plugin.constants.Message;
import network.skulk.plugin.extensions.tpa.TPAExtension;
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

    public TPACancelCommand(@NotNull TPAExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "tpa-cancel");
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

        String playerName = player.getName();

        Player target;
        String targetName;

        if (args.length == 1) {
            targetName = args[0];

        } else {
            ArrayList<String> playerOutGoingRequests = new ArrayList<>();

            for (Map.Entry<String, HashSet<String>> entry : extension.tpaRequests.entrySet()) {
                if (entry.getValue().contains(playerName)) {
                    playerOutGoingRequests.add(entry.getKey());
                }
            }

            int playerOutGoingRequestsSize = playerOutGoingRequests.size();

            if (playerOutGoingRequestsSize == 1) {
                targetName = playerOutGoingRequests.get(0);

            } else if (playerOutGoingRequestsSize == 0) {
                player.sendRichMessage(Message.NO_INCOMING_TPA_REQUESTS);
                return true;

            } else {
                StringBuilder response = new StringBuilder(Message.TPA_CANCEL_DIALOG);

                for (String toCancel : playerOutGoingRequests) {
                    response.append(Message.TPA_CANCEL_DIALOG_OPTION.formatted(toCancel, toCancel));
                }

                player.sendRichMessage(response.toString());
                return true;
            }
        }

        HashSet<String> targetIncomingRequests = extension.tpaRequests.computeIfAbsent(targetName, k -> new HashSet<>());

        if (!targetIncomingRequests.contains(playerName)) {
            player.sendRichMessage(Message.NO_OUTGOING_TPA_REQUEST_TO_X.formatted(targetName));
            return true;
        }

        target = Bukkit.getPlayerExact(targetName);

        player.sendRichMessage(Message.TPA_REQUEST_FROM_X_CANCELLED.formatted(targetName));

        if (target != null) {
            target.sendRichMessage(Message.TPA_REQUEST_FROM_X_CANCELLED_BY_SENDER.formatted(playerName));
        }

        return true;
    }
}
