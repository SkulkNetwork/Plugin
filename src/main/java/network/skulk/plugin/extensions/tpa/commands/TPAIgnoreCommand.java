package network.skulk.plugin.extensions.tpa.commands;

import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static network.skulk.helpers.MiniMessageHelper.sendMessage;

public final class TPAIgnoreCommand extends BaseCommand<TPAExtension> {
    public TPAIgnoreCommand(final @NotNull TPAExtension extension) {
        super(extension);
    }

    @Override protected void init() {
        this.name = "tpa-ignore";
        this.playerOnly = true;
        this.maxArgs = 1;
        this.minArgs = 1;
    }

    @Override protected boolean execute(final @NotNull Player player, final @NotNull String[] args) {
        final var targetName = args[0];

        if (!targetName.matches("\\w+")) {
            sendMessage(player, "red", '!', "Invalid player name: <b><0></b>", targetName);
            return true;
        }

        if (targetName.equalsIgnoreCase(player.getName())) {
            sendMessage(player, "red", '!', "You can't ignore yourself.");
            return true;
        }

        final var playerIgnores = this.getExtension().getTpaIgnores().get(player.getUniqueId());

        if (playerIgnores.contains("*")) {
            sendMessage(player, "red", '!', "You are already ignoring TPA requests from everyone.");
            return true;
        }

        for (final String ignoredName : playerIgnores) {
            if (ignoredName.equalsIgnoreCase(targetName)) {
                sendMessage(player, "red", '!', "You are already ignoring TPA requests from <b><0></b>.", ignoredName);
                return true;
            }
        }

        playerIgnores.add(targetName);

        sendMessage(player, "green", '✓', "You are now ignoring TPA requests from <b><0></b>.", targetName);

        return true;
    }
}
