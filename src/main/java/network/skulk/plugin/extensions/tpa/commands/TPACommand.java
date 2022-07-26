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

public final class TPACommand implements CommandExecutor {
    private final TPAExtension extension;

    public TPACommand(TPAExtension mainExtension) {
        extension = mainExtension;
        extension.register("tpa", this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            Message.sendOnlyPlayer(sender);
            return true;
        }
        if (args.length != 1) {
            return false;
        }

        String playerName = player.getName();

        Player target;
        String targetName = args[0];

        target = Bukkit.getPlayer(targetName);

        if (target == null || !target.isOnline()) {
            Message.sendPlayerOffline(player);
            return true;
        }

        HashSet<String> targetIgnores = extension.tpaIgnores.computeIfAbsent(targetName, k -> new HashSet<>());

        if (targetIgnores.contains("*")) {
            // Here the target ignores everyone.
            player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray> <red>%s</bold> doesn't accept TPA requests from anyone.</red>".formatted(targetName));
            return true;
        } else if (targetIgnores.contains(playerName)) {
            // Here the target ignores the player.
            player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray> <red>%s</bold> doesn't accept TPA requests from you.</red>".formatted(targetName));
            return true;
        }

        HashSet<String> targetIncomingRequests = extension.tpaRequests.computeIfAbsent(targetName, k -> new HashSet<>());

        // The player already has a TPA request sent to the target.
        if (targetIncomingRequests.contains(playerName)) {
            player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> <red>You already have a pending TPA request to <bold>%s</bold>.</red>".formatted(targetName));
            return true;
        }

        // Adds the request.
        targetIncomingRequests.add(playerName);

        // Removing the request 60 seconds later.
        extension.runTaskLater(60, () -> {
            if (targetIncomingRequests.contains(playerName)) {
                targetIncomingRequests.remove(playerName);
                player.sendRichMessage("<bold><gray>[ <color:#ffae1a>!</color> ]</gray></bold> <color:#ffae1a>Your TPA request to <bold>%s</bold> has expired.</color>".formatted(targetName));
                target.sendRichMessage("<bold><gray>[ <color:#ffae1a>!</color> ]</gray></bold> <color:#ffae1a>The TPA request <bold>%s</bold> has sent to you has expired.</color>".formatted(playerName));
            }
        });

        player.sendRichMessage("<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Sent a TPA request to <bold>%s</bold>.</green>".formatted(targetName));
        target.sendRichMessage("<bold><gray>[ <blue>?</blue> ]</gray> %s</bold> has sent a TPA request to you. Do you accept? <bold><green><click:run_command:/tpa-accept %s>[✓]</click></green> <red><click:run_command:/tpa-reject %s>[✗]</click></red></bold>".formatted(playerName, playerName, playerName));

        return true;
    }
}
