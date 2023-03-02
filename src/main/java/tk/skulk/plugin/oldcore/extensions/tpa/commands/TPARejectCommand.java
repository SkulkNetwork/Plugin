package tk.skulk.plugin.oldcore.extensions.tpa.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.oldcore.extensions.tpa.TPAExtension;
import tk.skulk.plugin.wrapper.BaseCommand;

import static tk.skulk.plugin.helpers.MiniMessageHelper.*;

public final class TPARejectCommand extends BaseCommand<TPAExtension> {
    public TPARejectCommand(final @NotNull TPAExtension extension) {
        super(extension);
    }

    @Override
    private void init() {
        this.setName("tpa-reject");
        this.setDescription("Rejects a TPA request sent to you.");
        this.setUsage("/tpa-reject [player]");
        this.setMaxArgs(1);
    }

    @Override
    private boolean execute(final @NotNull Player player, final @NotNull String[] args) {
        final var extension = this.getExtension();
        final var playerIncomingRequests = extension.getTpaRequests().get(player);

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
            final var component = Component.text().append(makeMessage("blue",
                '?',
                "Looks like multiple people want to TPA to you, which one would you like to reject?"
            ));

            for (final Player toReject : playerIncomingRequests.keySet()) {
                component.append(fmt(
                    "\n<b><gray>-></gray></b> <red><click:run_command:/tpa-reject <0>><0></click></red>",
                    toReject.getName()
                ));
            }

            player.sendMessage(component);

            return true;
        }

        final var targetName = target.getName();

        if (!playerIncomingRequests.containsKey(target)) {
            sendMessage(player, "red", '!', "<b><0></b> doesn't want to TPA to you.", targetName);
            return true;
        }

        playerIncomingRequests.get(target).cancel();
        playerIncomingRequests.remove(target);

        sendMessage(player, "green", '✓', "Rejected the TPA request from <b><0></b>.", targetName);
        sendMessage(player,
            "gold",
            '!',
            "<b><0></b> has rejected your TPA request.",
            player.getName()
        );
        return true;
    }
}
