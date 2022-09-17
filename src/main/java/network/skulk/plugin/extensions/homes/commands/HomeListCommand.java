package network.skulk.plugin.extensions.homes.commands;

import net.kyori.adventure.text.Component;
import network.skulk.plugin.extensions.homes.HomesExtension;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static network.skulk.helpers.MiniMessageHelper.*;

public final class HomeListCommand extends BaseCommand<HomesExtension> {
    public HomeListCommand(final @NotNull HomesExtension extension) {
        super(extension);
    }

    @Override protected void init() {
        this.name = "home-list";
        this.playerOnly = true;
        this.maxArgs = 0;
        this.minArgs = 0;
    }

    @Override protected boolean execute(final @NotNull Player player) {
        final var playerHomes = this.getExtension().getHomes().get(player.getUniqueId());

        if (playerHomes.isEmpty()) {
            sendMessage(player, "gold", '!', "You don't have any homes.");
            return true;
        }

        final var component = Component.text().append(
                makeMessage("gold", '!', "All homes:")
        );

        for (final Map.Entry<String, Location> entry : playerHomes.entrySet()) {
            final var l = entry.getValue();

            component.append(fmt("\n<b><gray>-></gray></b> <color:#ffae1a><0> (X: %.0f, Y: %.0f, Z: %.0f)</color>".formatted(
                    l.getX(), l.getY(), l.getZ()), entry.getKey()
            ));
        }

        player.sendMessage(component);

        return true;
    }
}
