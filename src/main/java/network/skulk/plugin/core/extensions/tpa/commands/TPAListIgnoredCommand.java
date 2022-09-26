package network.skulk.plugin.core.extensions.tpa.commands;

import net.kyori.adventure.text.Component;
import network.skulk.plugin.core.extensions.tpa.TPAExtension;
import network.skulk.plugin.wrapper.BaseCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static network.skulk.plugin.helpers.MiniMessageHelper.fmt;
import static network.skulk.plugin.helpers.MiniMessageHelper.makeMessage;
import static network.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

public final class TPAListIgnoredCommand extends BaseCommand<TPAExtension> {
    public TPAListIgnoredCommand(final @NotNull TPAExtension extension) {
        super(extension);
    }

    @Override protected void init() {
        this.setName("tpa-list-ignored");
        this.setDescription("Tells you all the people you have ignored.");
        this.setUsage("/tpa-list-ignored");
        this.setPlayerOnly(true);
        this.setMaxArgs(0);
        this.setMinArgs(0);
    }

    @Override protected boolean execute(final @NotNull Player player) {
        final var playerIgnores = this.getExtension().getTpaIgnores().get(player.getUniqueId());

        if (playerIgnores.isEmpty()) {
            sendMessage(player, "gold", '!', "You aren't ignoring anyone's TPA requests.");
            return true;
        }

        if (playerIgnores.contains("*")) {
            sendMessage(player, "gold", '!', "You are ignoring everyone's TPA requests.");
            return true;
        }

        final var component = Component.text().append(
                makeMessage("gold", '!', "You are ignoring the following people:")
        );

        for (final String ignored : playerIgnores) {
            component.append(fmt("\n<b><gray>-></gray></b> <color:#ffae1a><0></color>", ignored));
        }

        player.sendMessage(component);
        return true;
    }
}
