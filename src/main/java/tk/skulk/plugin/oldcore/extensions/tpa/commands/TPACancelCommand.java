package tk.skulk.plugin.oldcore.extensions.tpa.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.oldcore.extensions.tpa.TPAExtension;
import tk.skulk.plugin.wrapper.BaseCommand;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Map.Entry;
import static tk.skulk.plugin.helpers.MiniMessageHelper.*;

public final class TPACancelCommand extends BaseCommand<TPAExtension> {
    public TPACancelCommand(final @NotNull TPAExtension extension) {
        super(extension);
    }

    @Override
    private void init() {
        this.setName("tpa-cancel");
        this.setDescription("Cancels the TPA request you sent to a player.");
        this.setUsage("/tpa-cancel [player]");
        this.setMaxArgs(1);
    }

    @Override
    private boolean execute(final @NotNull Player player, final @NotNull String[] args) {
        final var playerName = player.getName();
        final Player target;

        final var tpaRequests = this.getExtension().getTpaRequests();

        if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sendMessage(player, "red", '!', "This player is offline.");
                return true;
            }
        }
        else {
            final var playerOutGoingRequests = new ArrayList<Player>();

            for (final Entry<Player, HashMap<Player, BukkitTask>> entry : tpaRequests.entrySet()) {
                if (entry.getValue().containsKey(player)) {
                    playerOutGoingRequests.add(entry.getKey());
                }
            }

            final int playerOutGoingRequestsSize = playerOutGoingRequests.size();

            if (playerOutGoingRequestsSize == 1) {
                target = playerOutGoingRequests.get(0);
            }
            else if (playerOutGoingRequestsSize == 0) {
                sendMessage(player, "red", '!', "You have no outgoing TPA requests.");
                return true;
            }
            else {
                final var component = Component.text().append(makeMessage("blue",
                    '!',
                    "Looks like you have multiple incoming TPA requests. Which one would you like to cancel?"
                ));

                // Twitter.
                for (final Player toCancel : playerOutGoingRequests) {
                    component.append(fmt(
                        "\n<b><gray>-></gray></b> <blue><click:run_command:/tpa-cancel <0>><0></click></blue>",
                        toCancel.getName()
                    ));
                }

                player.sendMessage(component);
                return true;
            }
        }

        final var targetName = target.getName();
        final var targetIncomingRequests = tpaRequests.get(target);

        if (!targetIncomingRequests.containsKey(player)) {
            sendMessage(player,
                "red",
                '!',
                "You don't have an outgoing request to <b><0></b>.",
                targetName
            );
            return true;
        }

        targetIncomingRequests.get(player).cancel();
        targetIncomingRequests.remove(player);

        sendMessage(player,
            "green",
            '✓',
            "Cancelled the TPA request going to <b><0></b>.",
            targetName
        );
        sendMessage(target,
            "gold",
            '!',
            "<b><0></b> has cancelled their TPA request to you.",
            playerName
        );
        return true;
    }
}
