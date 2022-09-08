package network.skulk.plugin.extensions.tpa.commands;

import net.kyori.adventure.text.Component;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static network.skulk.utils.MiniMessageFormat.*;

public final class TPARejectCommand extends BaseCommand<TPAExtension> {
    public TPARejectCommand(final TPAExtension extension) {
        super(extension);
    }

    @Override
    protected void init() {
        this.name = "tpa-reject";
        this.playerOnly = true;
        this.maxArgs = 1;
        this.minArgs = 0;
    }

    @Override
    protected boolean execute(final Player player, final String[] args) {
        final var playerName = player.getName();
        final var extension = this.getExtension();
        final var playerIncomingRequests = extension.getTpaRequests().get(playerName);

        if (playerIncomingRequests.isEmpty()) {
            sendMessage(player, "red", '!', "You have no incoming TPA requests.");
            return true;
        }

        final Player target;
        String targetName;

        if (args.length == 1) {
            targetName = args[0];

        } else if (playerIncomingRequests.size() == 1) {
            targetName = playerIncomingRequests.iterator().next();

        } else {
            final var component = Component.text().append(
                    makeMessage("blue", '?', "Looks like multiple people want to TPA to you, which one would you like to reject?")
            );

            for (final String toReject : playerIncomingRequests) {
                component.append(fmt("\n<b><gray>-></gray></b> <red><click:run_command:/tpa-reject <0>><0></click></red>", toReject));
            }

            player.sendMessage(component);

            return true;
        }

        target = Bukkit.getPlayer(targetName);

        if (target == null) {
            sendMessage(player, "red", '!', "This player is offline.");
            return true;
        }

        targetName = target.getName();

        if (!playerIncomingRequests.contains(targetName)) {
            sendMessage(player, "red", '!', "<b><0></b> doesn't want to TPA to you.", targetName);
            return true;
        }

        final var cancelTasks = extension.getTpaRequestCancelTasks().get(playerName);
        cancelTasks.get(targetName).cancel();
        cancelTasks.remove(targetName);

        playerIncomingRequests.remove(targetName);

        sendMessage(player, "green", '✓', "Rejected the TPA request from <b><0></b>.", targetName);
        sendMessage(player, "gold", '!', "<b><0></b> has rejected your TPA request.", playerName);

        return true;
    }
}
