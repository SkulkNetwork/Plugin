package tk.skulk.plugin.core.extensions.tpa.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.extensions.tpa.TPAExtension;
import tk.skulk.plugin.wrapper.BaseCommand;

import static tk.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

public final class TPACommand extends BaseCommand<TPAExtension> {
    public TPACommand(final @NotNull TPAExtension extension) {
        super(extension);
    }

    @Override
    protected void init() {
        this.setName("tpa");
        this.setDescription("Sends a TPA request to a player.");
        this.setUsage("/tpa <player>");
        this.setMaxArgs(1);
        this.setMinArgs(1);
    }

    @Override
    protected boolean execute(final @NotNull Player player, final @NotNull String[] args) {
        final var playerName = player.getName();

        final var target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sendMessage(player, "red", '!', "This player is offline.");
            return true;
        }

        final var targetName = target.getName();

        if (target == player) {
            sendMessage(player, "red", '!', "You can't TPA to yourself.");
            return true;
        }

        final var extension = this.getExtension();
        final var targetIgnores = extension.getTpaIgnores().get(target.getUniqueId());

        if (targetIgnores.contains("*")) {
            sendMessage(
                player,
                "red",
                '!',
                "<b><0></b> isn't accepting TPA requests from anyone.",
                targetName
            );
            return true;
        }

        for (final String blockedPlayerName : targetIgnores) {
            if (blockedPlayerName.equalsIgnoreCase(playerName)) {
                sendMessage(
                    player,
                    "red",
                    '!',
                    "<b><0></b> isn't accepting TPA requests from you.",
                    targetName
                );
                return true;
            }
        }

        final var targetTpaRequests = extension.getTpaRequests().get(target);

        if (targetTpaRequests.containsKey(player)) {
            sendMessage(
                player,
                "red",
                '!',
                "You already have a pending request to <b><0></b>",
                targetName
            );
            return true;
        }

        // This task will get cancelled when the player cancels their TPA request to this person
        // or the person accepts the request.
        targetTpaRequests.put(player, extension.getPlugin().runAfter(60, () -> {
            if (targetTpaRequests.containsKey(player)) {
                targetTpaRequests.remove(player);
                sendMessage(
                    player,
                    "gold",
                    '!',
                    "Your TPA request to <b><0></b> has expired.",
                    targetName
                );
                sendMessage(
                    target,
                    "gold",
                    '!',
                    "The TPA request <b><0></b> has sent you has expired.",
                    playerName
                );
            }
        }));

        sendMessage(player, "green", '✓', "Sent a TPA request to <b><0></b>", targetName);
        sendMessage(
            target,
            "blue",
            '?',
            "<b><0></b> wants to TPA to you. Do you accept? <b><green><click:run_command:/tpa-accept %s>[✓]</click></green> <red><click:run_command:/tpa-reject %s>[✗]</click></red></b>",
            playerName
        );
        return true;
    }
}
