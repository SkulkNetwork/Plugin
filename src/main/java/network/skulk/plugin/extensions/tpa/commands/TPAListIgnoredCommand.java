package network.skulk.plugin.extensions.tpa.commands;

import net.kyori.adventure.text.Component;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.entity.Player;

import static network.skulk.utils.MiniMessageHelper.*;

public final class TPAListIgnoredCommand extends BaseCommand<TPAExtension> {
    public TPAListIgnoredCommand(final TPAExtension extension) {
        super(extension);
    }

    @Override
    protected void init() {
        this.name = "tpa-list-ignored";
        this.playerOnly = true;
        this.maxArgs = 0;
        this.minArgs = 0;
    }

    @Override
    protected boolean execute(final Player player) {
        final var playerIgnores = this.getExtension().getTpaIgnores().get(player.getName());

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
