package network.skulk.pluginrewrite.extensions.tpa.commands;

import network.skulk.pluginrewrite.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static network.skulk.utils.MessageFormat.sendMessage;

public final class TPACommand extends BaseCommand<TPAExtension> {
    @Override
    protected void init() {
        this.aliases = new String[]{"tpa"};
        this.playerOnly = true;
        this.maxArgs = 1;
        this.minArgs = 1;
    }

    @Override
    protected boolean execute(final Player player, final String[] args) {
        final var playerName = player.getName();

        String targetName = args[0];

        if (targetName.equalsIgnoreCase(playerName)) {
            sendMessage(player, "red", '!', "You can't TPA to yourself!");
            return true;
        }

        final var target = Bukkit.getPlayer(targetName);

        if (target == null) {
            sendMessage(player, "red", '!', "This player is offline!");
            return true;
        }

        targetName = target.getName();

        final var extension = this.getExtension();
        final var targetIgnores = extension.getTpaIgnores().get(targetName);

        if (targetIgnores.contains("*")) {
            sendMessage(player, "red", '!', "<b><0></b> isn't accepting TPA requests from anyone.", targetName);
            return true;
        }

        if (targetIgnores.contains(playerName.toLowerCase())) {
            sendMessage(player, "red", '!', "<b><0></b> isn't accepting TPA requests from you.", targetName);
            return true;
        }

        final var tpaRequests = extension.getTpaRequests();

        if (tpaRequests.containsEntry(targetName, playerName)) {
            sendMessage(player, "red", '!', "You already have a pending request to <b><0></b>", targetName);
            return true;
        }

        tpaRequests.put(targetName, playerName);

        final var finalTargetName = targetName;
        // This task will get cancelled when the player cancels their TPA request to this person.
        extension.getTpaRequestCancelTasks().get(targetName).put(playerName, this.runAfter(1200, () -> {
            if (tpaRequests.containsEntry(finalTargetName, playerName)) {
                tpaRequests.remove(finalTargetName, playerName);
                sendMessage(player, "orange", '!', "Your TPA request to <b><0></b> has expired.", finalTargetName);
                sendMessage(target, "orange", '!', "The TPA request <b><0></b> has sent you has expired.", playerName);
            }
        }));

        return true;
    }
}
