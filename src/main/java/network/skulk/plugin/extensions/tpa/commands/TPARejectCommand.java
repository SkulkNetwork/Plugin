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

public final class TPARejectCommand implements CommandExecutor {
    private final @NotNull TPAExtension extension;

    public TPARejectCommand(@NotNull final TPAExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "tpa-reject");
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
        //noinspection DuplicatedCode
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ONLY_PLAYERS_CAN_USE_THIS_COMMAND);
            return true;
        }

        if (args.length > 1) {
            return false;
        }

        final String playerName = player.getName();
        final HashSet<String> playerIncomingRequests = extension.tpaRequests.computeIfAbsent(playerName, k -> new HashSet<>());
        final int playerIncomingRequestsSize = playerIncomingRequests.size();

        if (playerIncomingRequestsSize == 0) {
            player.sendRichMessage(Message.NO_INCOMING_TPA_REQUESTS);
            return true;
        }

        final Player target;
        String targetName;

        if (args.length == 1) {
            targetName = args[0];

        } else if (playerIncomingRequestsSize != 1) {
            final StringBuilder response = new StringBuilder(Message.TPA_REJECT_DIALOG);

            for (final String toReject : playerIncomingRequests) {
                response.append(Message.TPA_REJECT_DIALOG_OPTION.formatted(toReject, toReject));
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

        playerIncomingRequests.remove(targetName);

        player.sendRichMessage(Message.TPA_REQUEST_FROM_X_REJECTED.formatted(targetName));
        target.sendRichMessage(Message.X_HAS_REJECTED_YOUR_TPA_REQUEST.formatted(playerName));

        return true;
    }
}
