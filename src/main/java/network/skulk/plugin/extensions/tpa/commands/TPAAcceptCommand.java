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
    private final TPAExtension extension;

    public TPAAcceptCommand(TPAExtension mainExtension) {
        extension = mainExtension;
        extension.register("tpa-accept", this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player user)) {
            Message.sendOnlyPlayer(sender);
            return true;
        }
        if (args.length > 1) return false;

        String userName = user.getName();
        HashSet<String> userIncomingRequests = extension.tpaRequests.computeIfAbsent(userName, k -> new HashSet<>());

        Player target;
        String targetName;

        if (args.length == 1) {
            targetName = args[0];
        } else {
            // No target was specified.
            if (userIncomingRequests.size() == 1) {
                // User has 1 incoming TPA request.
                targetName = userIncomingRequests.iterator().next();
            } else if (userIncomingRequests.size() == 0) {
                // User has no incoming TPA requests.
                user.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> <red>You don't have any incoming TPA requests.</red>");
                return true;
            } else {
                // Multiple people want to TPA to the user.
                StringBuilder response = new StringBuilder()
                        .append("<bold><gray>[ <blue>?</blue> ]</gray></bold> <blue>Seems like you have multiple people wanting to TPA to you. Which one would you like to accept?</blue>");

                for (String p : userIncomingRequests) {
                    response.append("\n<bold><green><click:run_command:/tpa-accept ")
                            .append(p)
                            .append("[")
                            .append(p)
                            .append("]</click><green></bold>");
                }

                user.sendRichMessage(response.toString());
                return true;
            }
        }

        target = Bukkit.getPlayer(targetName);

        if (target == null || !target.isOnline()) {
            Message.sendPlayerOffline(user);
            return true;
        }

        if (!userIncomingRequests.contains(targetName)) {
            user.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray> <red>%s</bold> doesn't want to TPA to you.</red>".formatted(targetName));
            return true;
        }

        user.sendRichMessage("<bold><gray>[ <green>!</green> ]</gray></bold> <green>Teleporting <bold>%s</bold> to you...</green>".formatted(targetName));
        target.sendRichMessage("<bold><gray>[ <green>!</green> ]</gray></bold> <green>Teleporting you to <bold>%s</bold>...</green>".formatted(userName));

        target.teleport(user);
        userIncomingRequests.remove(targetName);

        return true;
    }
}
