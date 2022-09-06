package network.skulk.plugin.extensions.tpa.commands;

import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.entity.Player;

import static network.skulk.utils.MiniMessageFormat.sendMessage;

public final class TPAIgnoreCommand extends BaseCommand<TPAExtension> {

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

        final var playerIgnores = this.getExtension().getTpaIgnores().get(player.getName());
        final var targetNameLower = targetName.toLowerCase();

        if (playerIgnores.contains("*")) {
            sendMessage(player, "red", '!', "You are already ignoring TPA requests from everyone.");
            return true;
        }

        if (playerIgnores.contains(targetNameLower)) {
            sendMessage(player, "red", '!', "You are already ignoring TPA requests from <b><0></b>.", targetName);
            return true;
        }

        playerIgnores.add(targetNameLower);

        sendMessage(player, "green", '✓', "You are now ignoring TPA requests from <b><0></b>.", targetName);

        return true;
    }
}
