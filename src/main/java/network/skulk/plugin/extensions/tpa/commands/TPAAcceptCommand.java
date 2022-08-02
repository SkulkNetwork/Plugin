package network.skulk.plugin.extensions.tpa.commands;

import network.skulk.plugin.constants.Message;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class TPAAcceptCommand implements CommandExecutor {
    private final @NotNull TPAExtension extension;

    public TPAAcceptCommand(@NotNull TPAExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "tpa-accept");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //noinspection DuplicatedCode
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ONLY_PLAYERS_CAN_USE_THIS_COMMAND);
            return true;
        }

        if (args.length > 1) {
            return false;
        }

        String playerName = player.getName();
        HashSet<String> playerIncomingRequests = extension.tpaRequests.computeIfAbsent(playerName, k -> new HashSet<>());
        int playerIncomingRequestsSize = playerIncomingRequests.size();

        if (playerIncomingRequestsSize == 0) {
            player.sendRichMessage(Message.NO_INCOMING_TPA_REQUESTS);
            return true;
        }

        Player target;
        String targetName;

        if (args.length == 1) {
            targetName = args[0];

        } else if (playerIncomingRequestsSize != 1) {
            StringBuilder response = new StringBuilder(Message.TPA_ACCEPT_DIALOG);

            for (String toAccept : playerIncomingRequests) {
                response.append(Message.TPA_ACCEPT_DIALOG_OPTION.formatted(toAccept, toAccept));
            }

            player.sendRichMessage(response.toString());
            return true;

        } else {
            // Args is 0 and playerIncomingRequestsSize is 1.
            targetName = playerIncomingRequests.iterator().next();
        }

        //noinspection DuplicatedCode
        target = Bukkit.getPlayerExact(targetName);

        if (target == null) {
            player.sendRichMessage(Message.PLAYER_NOT_ONLINE);
            return true;
        }

        targetName = target.getName();

        if (!playerIncomingRequests.contains(targetName)) {
            player.sendRichMessage(Message.X_DOESNT_WANT_TO_TPA_TO_YOU.formatted(targetName));
            return true;
        }

        player.sendRichMessage(Message.TELEPORTING_X_TO_YOU.formatted(targetName));
        target.sendRichMessage(Message.TELEPORTING_YOU_TO_X.formatted(playerName));

        target.teleport(player);
        playerIncomingRequests.remove(targetName);

        return true;
    }
}
