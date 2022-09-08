package network.skulk.plugin.extensions.tpa.commands;

import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.entity.Player;

import static network.skulk.utils.MiniMessageFormat.sendMessage;

public final class TPAIgnoreCommand extends BaseCommand<TPAExtension> {
    public TPAIgnoreCommand(final TPAExtension extension) {
        super(extension);
    }

    @Override
    protected void init() {
        this.name = "tpa-ignore";
        this.playerOnly = true;
        this.maxArgs = 1;
        this.minArgs = 1;
    }

    @Override
    protected boolean execute(final Player player, final String[] args) {
        final var targetName = args[0];

        if (!targetName.matches("\\w+")) {
            sendMessage(player, "red", '!', "Invalid player name: <b><0></b>", targetName);
            return true;
        }

        final var playerName = player.getName();

        if (targetName.equalsIgnoreCase(playerName)) {
            sendMessage(player, "red", '!', "You can't ignore yourself.");
            return true;
        }

        final var playerIgnores = this.getExtension().getTpaIgnores().get(playerName);

        if (playerIgnores.contains("*")) {
            sendMessage(player, "red", '!', "You are already ignoring TPA requests from everyone.");
            return true;
        }

        for (final String blockedPlayerName : playerIgnores) {
            if (blockedPlayerName.equalsIgnoreCase(targetName)) {
                sendMessage(player, "red", '!', "You are already ignoring TPA requests from <b><0></b>.", blockedPlayerName);
                return true;
            }
        }

        playerIgnores.add(targetName);

        sendMessage(player, "green", '✓', "You are now ignoring TPA requests from <b><0></b>.", targetName);

        return true;
    }
}
