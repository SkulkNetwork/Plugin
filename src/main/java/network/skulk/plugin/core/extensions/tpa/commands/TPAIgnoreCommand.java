package network.skulk.plugin.core.extensions.tpa.commands;

import network.skulk.plugin.core.extensions.tpa.TPAExtension;
import network.skulk.plugin.helpers.MiniMessageHelper;
import network.skulk.plugin.wrapper.BaseCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class TPAIgnoreCommand extends BaseCommand<TPAExtension> {
    public TPAIgnoreCommand(final @NotNull TPAExtension extension) {
        super(extension);
    }

    @Override protected void init() {
        this.setName("tpa-ignore");
        this.setDescription("Ignores a player's TPA requests until the command is ran again for the player.");
        this.setUsage("/tpa-ignore <player>");
        this.setPlayerOnly(true);
        this.setMaxArgs(1);
        this.setMinArgs(1);
    }

    @Override protected boolean execute(final @NotNull Player player, final @NotNull String[] args) {
        final var targetName = args[0];

        if (!targetName.matches("\\w+")) {
            MiniMessageHelper.sendMessage(player, "red", '!', "Invalid player name: <b><0></b>", targetName);
            return true;
        }

        if (targetName.equalsIgnoreCase(player.getName())) {
            MiniMessageHelper.sendMessage(player, "red", '!', "You can't ignore yourself.");
            return true;
        }

        final var playerIgnores = this.getExtension().getTpaIgnores().get(player.getUniqueId());

        if (playerIgnores.contains("*")) {
            MiniMessageHelper.sendMessage(player, "red", '!', "You are already ignoring TPA requests from everyone.");
            return true;
        }

        for (final String ignoredName : playerIgnores) {
            if (ignoredName.equalsIgnoreCase(targetName)) {
                MiniMessageHelper.sendMessage(player, "red", '!', "You are already ignoring TPA requests from <b><0></b>.", ignoredName);
                return true;
            }
        }

        playerIgnores.add(targetName);

        MiniMessageHelper.sendMessage(player, "green", '✓', "You are now ignoring TPA requests from <b><0></b>.", targetName);
        return true;
    }
}
