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
        if (!(sender instanceof Player user)) {
            Message.sendOnlyPlayer(sender);
            return true;
        }
        if (args.length != 1) return false;

        String userName = user.getName();

        String targetName = args[0];
        Player target = Bukkit.getPlayer(targetName);

        if (target == null || !target.isOnline()) {
            Message.sendPlayerOffline(user);
            return true;
        }

        HashSet<String> targetIgnores = extension.tpaIgnores.computeIfAbsent(targetName, k -> new HashSet<>());

        if (targetIgnores.contains("*")) {
            // Here the target ignores everyone.
            user.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray> <red>%s</bold> doesn't accept TPA requests from anybody.</red>".formatted(targetName));
            return true;
        } else if (targetIgnores.contains(userName)) {
            // Here the target ignores the user.
            user.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray> <red>%s</bold> doesn't accept TPA requests from you.</red>".formatted(targetName));
            return true;
        }

        HashSet<String> targetRequests = extension.tpaRequests.computeIfAbsent(targetName, k -> new HashSet<>());

        // The user already has a TPA request sent to the target.
        if (targetRequests.contains(userName)) {
            user.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> <red>You already have a pending TPA request to <bold>%s</bold>.</red>".formatted(targetName));
            return true;
        }

        // Adds the request.
        targetRequests.add(userName);

        // Removing the request 60 seconds later.
        extension.runTaskLater(60, () -> {
            if (targetRequests.contains(userName)) {
                targetRequests.remove(userName);
                user.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> <red>Your TPA request to <bold>%s</bold> has expired.</red>".formatted(targetName));
                target.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> <red>The TPA request <bold>%s</bold> has sent to you has expired.</red>".formatted(userName));
            }
        });

        user.sendRichMessage("<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Sent a TPA request to <bold>%s</bold>.</green>".formatted(targetName));
        target.sendRichMessage("<bold><gray>[ <blue>?</blue> ]</gray> %s</bold> has sent a TPA request to you. Do you accept? <bold><green><click:run_command:/tpa-accept %s>[✓]</click> </green><red><click:run_command:/tpa-reject %s>[✗]</click></red></bold>".formatted(userName, userName, userName));

        return true;
    }
}
