package network.skulk.plugin.extensions.tpa.commands;

import net.kyori.adventure.text.Component;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.TreeMap;

import static network.skulk.helpers.MiniMessageHelper.*;

public final class TPACancelCommand extends BaseCommand<TPAExtension> {
    public TPACancelCommand(final TPAExtension extension) {
        super(extension);
    }

    @Override
    protected void init() {
        this.name = "tpa-cancel";
        this.playerOnly = true;
        this.maxArgs = 1;
        this.minArgs = 0;
    }

    @Override
    protected boolean execute(final Player player, final String[] args) {
        final var playerName = player.getName();
        String targetName;

        final var tpaRequests = this.getExtension().getTpaRequests();

        if (args.length == 1) {
            targetName = args[0];

        } else {
            final var playerOutGoingRequests = new ArrayList<String>();

            for (final String target : tpaRequests.keySet()) {
                if (tpaRequests.get(target).containsKey(playerName)) {
                    playerOutGoingRequests.add(target);
                }
            }

            final int prs = playerOutGoingRequests.size();

            if (prs == 1) {
                targetName = playerOutGoingRequests.get(0);

            } else if (prs == 0) {
                sendMessage(player, "red", '!', "You have no outgoing TPA requests.");
                return true;

            } else {
                final var component = Component.text().append(
                        makeMessage("blue", '!', "Looks like you have multiple incoming TPA requests. Which one would you like to cancel?")
                );

                // Twitter.
                for (final String toCancel : playerOutGoingRequests) {
                    component.append(fmt("\n<b><gray>-></gray></b> <blue><click:run_command:/tpa-cancel <0>><0></click></blue>", toCancel));
                }

                player.sendMessage(component);
                return true;
            }
        }

        final var target = Bukkit.getPlayer(targetName);

        if (target == null) {
            sendMessage(player, "red", '!', "This player is offline.");
            return true;
        }

        targetName = target.getName();

        final var targetIncomingRequests = tpaRequests.computeIfAbsent(targetName, k -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER));

        if (!targetIncomingRequests.containsKey(playerName)) {
            sendMessage(player, "red", '!', "You don't have an outgoing request to <b><0></b>.", targetName);
            return true;
        }

        targetIncomingRequests.get(playerName).cancel();
        targetIncomingRequests.remove(playerName);

        targetIncomingRequests.remove(playerName);

        sendMessage(player, "green", '✓', "Cancelled the TPA request going to <b><0></b>.", targetName);
        sendMessage(target, "gold", '!', "<b><0></b> has cancelled their TPA request to you.", playerName);

        return true;
    }
}
