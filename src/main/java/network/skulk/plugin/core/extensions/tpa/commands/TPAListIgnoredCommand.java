package network.skulk.plugin.core.extensions.tpa.commands;

import net.kyori.adventure.text.Component;
import network.skulk.plugin.core.extensions.tpa.TPAExtension;
import network.skulk.plugin.helpers.MiniMessageHelper;
import network.skulk.plugin.wrapper.BaseCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class TPAListIgnoredCommand extends BaseCommand<TPAExtension> {
    public TPAListIgnoredCommand(final @NotNull TPAExtension extension) {
        super(extension);
    }

    @Override protected void init() {
        this.name = "tpa-list-ignored";
        this.playerOnly = true;
        this.maxArgs = 0;
        this.minArgs = 0;
    }

    @Override protected boolean execute(final @NotNull Player player) {
        final var playerIgnores = this.getExtension().getTpaIgnores().get(player.getUniqueId());

        if (playerIgnores.isEmpty()) {
            MiniMessageHelper.sendMessage(player, "gold", '!', "You aren't ignoring anyone's TPA requests.");
            return true;
        }

        if (playerIgnores.contains("*")) {
            MiniMessageHelper.sendMessage(player, "gold", '!', "You are ignoring everyone's TPA requests.");
            return true;
        }

        final var component = Component.text().append(
                MiniMessageHelper.makeMessage("gold", '!', "You are ignoring the following people:")
        );

        for (final String ignored : playerIgnores) {
            component.append(MiniMessageHelper.fmt("\n<b><gray>-></gray></b> <color:#ffae1a><0></color>", ignored));
        }

        player.sendMessage(component);
        return true;
    }
}
