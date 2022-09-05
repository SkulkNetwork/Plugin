package network.skulk.pluginrewrite.extensions.tpa.commands;

import net.kyori.adventure.text.Component;
import network.skulk.pluginrewrite.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static network.skulk.utils.MiniMessageFormat.*;

public final class TPAAcceptCommand extends BaseCommand<TPAExtension> {

    @Override
    protected void init() {
        this.aliases = new String[]{"tpa-accept"};
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
                    makeMessage("blue", '?', "Looks like multiple people want to TPA to you, which one would you like to accept?")
            );

            for (final String toAccept : playerIncomingRequests) {
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

        if (!playerIncomingRequests.contains(targetName)) {
            sendMessage(player, "red", '!', "<b><0></b> doesn't want to TPA to you.", targetName);
            return true;
        }

        // Effects start.
        final var targetWorld = target.getWorld();
        final var targetLocation = target.getLocation();

        // TODO: does this look good?
        targetWorld.playSound(targetLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        targetWorld.spawnParticle(Particle.PORTAL, targetLocation, 5);

        final var playerWorld = player.getWorld();
        final var playerLocation = player.getLocation();

        playerWorld.playSound(playerLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        playerWorld.spawnParticle(Particle.PORTAL, playerLocation, 5);
        // Effects end.

        sendMessage(player, "green", '!', "Teleporting <b><0></b> to you...", targetName);
        sendMessage(player, "green", '!', "Teleporting you to <b><0></b>...", playerName);

        final var cancelTasks = extension.getTpaRequestCancelTasks().get(targetName);
        cancelTasks.get(playerName).cancel();
        cancelTasks.remove(playerName);

        target.teleport(player);
        playerIncomingRequests.remove(targetName);

        return true;
    }
}