package network.skulk.plugin.extensions.tpa.commands;

import net.kyori.adventure.text.Component;
import network.skulk.helpers.EffectHelper;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.TreeMap;

import static network.skulk.helpers.MiniMessageHelper.*;

public final class TPAAcceptCommand extends BaseCommand<TPAExtension> {
    public TPAAcceptCommand(final TPAExtension extension) {
        super(extension);
    }

    @Override
    protected void init() {
        this.name = "tpa-accept";
        this.playerOnly = true;
        this.maxArgs = 1;
        this.minArgs = 0;
    }


    @Override
    protected boolean execute(final Player player, final String[] args) {
        final var playerName = player.getName();
        final var extension = this.getExtension();
        final var playerIncomingRequests = extension.getTpaRequests().computeIfAbsent(playerName, k -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER));

        if (playerIncomingRequests.isEmpty()) {
            sendMessage(player, "red", '!', "You have no incoming TPA requests.");
            return true;
        }

        final Player target;
        String targetName;

        if (args.length == 1) {
            targetName = args[0];

        } else if (playerIncomingRequests.size() == 1) {
            targetName = playerIncomingRequests.firstKey();

        } else {
            final var component = Component.text().append(
                    makeMessage("blue", '?', "Looks like multiple people want to TPA to you, which one would you like to accept?")
            );

            for (final String toAccept : playerIncomingRequests.keySet()) {
                component.append(fmt("\n<b><gray>-></gray></b> <green><click:run_command:/tpa-accept <0>><0></click></green>", toAccept));
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

        if (!playerIncomingRequests.containsKey(targetName)) {
            sendMessage(player, "red", '!', "<b><0></b> doesn't want to TPA to you.", targetName);
            return true;
        }

        sendMessage(player, "green", '!', "Teleporting <b><0></b> to you...", targetName);
        sendMessage(player, "green", '!', "Teleporting you to <b><0></b>...", playerName);

        EffectHelper.playTeleport(target);
        EffectHelper.playTeleport(player);

        playerIncomingRequests.get(targetName).cancel();

        target.teleport(player);
        playerIncomingRequests.remove(targetName);

        return true;
    }
}
