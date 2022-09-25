package network.skulk.plugin.core.extensions.tpa.commands;

import net.kyori.adventure.text.Component;
import network.skulk.plugin.core.extensions.tpa.TPAExtension;
import network.skulk.plugin.helpers.EffectHelper;
import network.skulk.plugin.wrapper.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static network.skulk.plugin.helpers.MiniMessageHelper.*;

public final class TPAAcceptCommand extends BaseCommand<TPAExtension> {
    public TPAAcceptCommand(final @NotNull TPAExtension extension) {
        super(extension);
    }

    @Override protected void init() {
        this.name = "tpa-accept";
        this.playerOnly = true;
        this.maxArgs = 1;
        this.minArgs = 0;
    }

    @Override protected boolean execute(final @NotNull Player player, final @NotNull String[] args) {
        final var playerIncomingRequests = this.getExtension().getTpaRequests().get(player);

        if (playerIncomingRequests.isEmpty()) {
            sendMessage(player, "red", '!', "You have no incoming TPA requests.");
            return true;
        }

        final Player target;

        if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sendMessage(player, "red", '!', "This player is offline.");
                return true;
            }
        }
        else if (playerIncomingRequests.size() == 1) {
            target = playerIncomingRequests.keySet().iterator().next();
        }
        else {
            final var component = Component.text().append(
                    makeMessage("blue", '?', "Looks like multiple people want to TPA to you, which one would you like to accept?")
            );

            for (final Player toAccept : playerIncomingRequests.keySet()) {
                component.append(
                        fmt("\n<b><gray>-></gray></b> <green><click:run_command:/tpa-accept <0>><0></click></green>", toAccept.getName())
                );
            }

            player.sendMessage(component);
            return true;
        }

        final var targetName = target.getName();

        if (!playerIncomingRequests.containsKey(target)) {
            sendMessage(player, "red", '!', "<b><0></b> doesn't want to TPA to you.", targetName);
            return true;
        }

        sendMessage(player, "green", '!', "Teleporting <b><0></b> to you...", targetName);
        sendMessage(player, "green", '!', "Teleporting you to <b><0></b>...", player.getName());

        EffectHelper.playTeleport(target);
        EffectHelper.playTeleport(player);

        playerIncomingRequests.get(target).cancel();
        playerIncomingRequests.remove(target);

        target.teleport(player);
        return true;
    }
}
