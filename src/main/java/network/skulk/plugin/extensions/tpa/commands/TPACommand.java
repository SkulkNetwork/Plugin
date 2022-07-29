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

public final class TPACommand implements CommandExecutor {
    private final @NotNull TPAExtension extension;

    public TPACommand(@NotNull TPAExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "tpa");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.CONSOLE_NOT_ALLOWED);
            return true;
        } else if (args.length != 1) {
            return false;
        }

        String playerName = player.getName();

        Player target;
        String targetName = args[0];

        target = Bukkit.getPlayer(targetName);

        if (target == null || !target.isOnline()) {
            player.sendRichMessage(Message.PLAYER_OFFLINE);
            return true;
        }

//        HashSet<String> targetIgnores = extension.tpaIgnores.computeIfAbsent(targetName, k -> new HashSet<>());

//        if (targetIgnores.contains("*")) {
//            // Here the target ignores everyone.
//            player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray> <red>%s</bold> doesn't accept TPA requests from anyone.</red>".formatted(targetName));
//            return true;
//        } else if (targetIgnores.contains(playerName)) {
//            // Here the target ignores the player.
//            player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray> <red>%s</bold> doesn't accept TPA requests from you.</red>".formatted(targetName));
//            return true;
//        }

        HashSet<String> targetIncomingRequests = extension.tpaRequests.computeIfAbsent(targetName, k -> new HashSet<>());

        // The player already has a TPA request sent to the target.
        if (targetIncomingRequests.contains(playerName)) {
            player.sendRichMessage(Message.ALREADY_HAS_PENDING_TPA_REQUEST.formatted(targetName));
            return true;
        }

        // Adds the request.
        targetIncomingRequests.add(playerName);

        // Removing the request 60 seconds later.
        extension.plugin.runLater(60, () -> {
            if (targetIncomingRequests.contains(playerName)) {
                targetIncomingRequests.remove(playerName);
                player.sendRichMessage(Message.TPA_REQUEST_TO_X_EXPIRED.formatted(targetName));
                target.sendRichMessage(Message.TPA_REQUEST_X_SEND_TO_YOU_EXPIRED.formatted(playerName));
            }
        });

        player.sendRichMessage(Message.TPA_REQUEST_SENT_TO_X.formatted(targetName));
        target.sendRichMessage(Message.TPA_REQUEST_SENT_BY_X_RECEIVED.formatted(playerName, playerName, playerName));

        return true;
    }
}
