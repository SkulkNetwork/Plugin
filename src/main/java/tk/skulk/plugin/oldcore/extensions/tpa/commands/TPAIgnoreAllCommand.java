package tk.skulk.plugin.oldcore.extensions.tpa.commands;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.oldcore.extensions.tpa.TPAExtension;
import tk.skulk.plugin.wrapper.BaseCommand;

import static tk.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

public final class TPAIgnoreAllCommand extends BaseCommand<TPAExtension> {
    public TPAIgnoreAllCommand(final @NotNull TPAExtension extension) {
        super(extension);
    }

    @Override
    private void init() {
        this.setName("tpa-ignore-all");
        this.setDescription("Ignores all TPA requests sent to you until the command is ran again.");
        this.setUsage("/tpa-ignore-all");
    }

    @Override
    private boolean execute(final @NotNull Player player) {
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
