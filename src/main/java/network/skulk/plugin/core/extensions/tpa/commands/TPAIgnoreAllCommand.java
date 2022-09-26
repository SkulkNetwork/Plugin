package network.skulk.plugin.core.extensions.tpa.commands;

import network.skulk.plugin.core.extensions.tpa.TPAExtension;
import network.skulk.plugin.wrapper.BaseCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static network.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

public final class TPAIgnoreAllCommand extends BaseCommand<TPAExtension> {
    public TPAIgnoreAllCommand(final @NotNull TPAExtension extension) {
        super(extension);
    }

    @Override protected void init() {
        this.setName("tpa-ignore-all");
        this.setDescription("Ignores all TPA requests sent to you until the command is ran again.");
        this.setUsage("/tpa-ignore-all");
        this.setPlayerOnly(true);
        this.setMaxArgs(0);
        this.setMinArgs(0);
    }

    @Override protected boolean execute(final @NotNull Player player) {
        final var playerIgnores = this.getExtension().getTpaIgnores().get(player.getUniqueId());

        final String message;

        if (playerIgnores.contains("*")) {
            playerIgnores.remove("*");
            message = "You are now <b>not</b> ignoring everyone's TPA requests.";
        }
        else {
            playerIgnores.add("*");
            message = "You are now ignoring everyone's TPA requests.";
        }

        sendMessage(player, "green", '✓', message);
        return true;
    }
}
