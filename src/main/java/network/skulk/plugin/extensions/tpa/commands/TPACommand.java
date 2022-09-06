package network.skulk.plugin.extensions.tpa.commands;

import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static network.skulk.utils.MiniMessageFormat.sendMessage;

public final class TPACommand extends BaseCommand<TPAExtension> {
    @Override
    protected void init() {
        this.name = "tpa";
        this.playerOnly = true;
        this.maxArgs = 1;
        this.minArgs = 1;
    }

    @Override
    protected boolean execute(final Player player, final String[] args) {
        final var playerName = player.getName();

        var targetName = args[0];

        if (targetName.equalsIgnoreCase(playerName)) {
            sendMessage(player, "red", '!', "You can't TPA to yourself.");
            return true;
        }

        final var target = Bukkit.getPlayer(targetName);

        if (target == null) {
            sendMessage(player, "red", '!', "This player is offline.");
            return true;
        }

        targetName = target.getName();

        final var extension = this.getExtension();
        final var targetIgnores = extension.getTpaIgnores().get(targetName);

        if (targetIgnores.contains("*")) {
            sendMessage(player, "red", '!', "<b><0></b> isn't accepting TPA requests from anyone.", targetName);
            return true;
        }

        for (final String blockedPlayerName : targetIgnores) {
            if (blockedPlayerName.equalsIgnoreCase(playerName)) {
                sendMessage(player, "red", '!', "<b><0></b> isn't accepting TPA requests from you.", targetName);
                return true;
            }
        }

        final var targetTpaRequests = extension.getTpaRequests().get(targetName);

        if (targetTpaRequests.contains(playerName)) {
            sendMessage(player, "red", '!', "You already have a pending request to <b><0></b>", targetName);
            return true;
        }

        targetTpaRequests.add(playerName);

        final var finalTargetName = targetName;
        // This task will get cancelled when the player cancels their TPA request to this person
        // or the person accepts the request.
        extension.getTpaRequestCancelTasks().get(targetName).put(playerName, this.runAfter(60 * 20, () -> {
            if (targetTpaRequests.contains(playerName)) {
                targetTpaRequests.remove(playerName);
                sendMessage(player, "orange", '!', "Your TPA request to <b><0></b> has expired.", finalTargetName);
                sendMessage(target, "orange", '!', "The TPA request <b><0></b> has sent you has expired.", playerName);
            }
        }));

        sendMessage(player, "green", '✓', "Sent a TPA request to <b><0></b>", targetName);
        sendMessage(target, "blue", '?', "<b><0></b> wants to TPA to you. Do you accept? <b><green><click:run_command:/tpa-accept %s>[✓]</click></green> <red><click:run_command:/tpa-reject %s>[✗]</click></red></b>", playerName);

        return true;
    }
}
