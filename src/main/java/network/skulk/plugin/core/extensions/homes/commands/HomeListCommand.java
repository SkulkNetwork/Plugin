package network.skulk.plugin.core.extensions.homes.commands;

import net.kyori.adventure.text.Component;
import network.skulk.plugin.core.extensions.homes.HomesExtension;
import network.skulk.plugin.utils.Location;
import network.skulk.plugin.wrapper.BaseCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static java.util.Map.Entry;
import static network.skulk.plugin.helpers.MiniMessageHelper.fmt;
import static network.skulk.plugin.helpers.MiniMessageHelper.makeMessage;
import static network.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

public final class HomeListCommand extends BaseCommand<HomesExtension> {
    public HomeListCommand(final @NotNull HomesExtension extension) {
        super(extension);
    }

    @Override protected void init() {
        this.setName("home-list");
        this.setDescription("Tells you all your homes.");
        this.setUsage("/home-list");
        this.setPlayerOnly(true);
        this.setMaxArgs(0);
        this.setMinArgs(0);
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

        for (final Entry<String, Location> entry : playerHomes.entrySet()) {
            final var location = entry.getValue();

            component.append(
                    fmt(
                            "\n<b><gray>-></gray></b> <color:#ffae1a><0> (X: %.0f, Y: %.0f, Z: %.0f)</color>".formatted(
                                    location.getX(), location.getY(), location.getZ()
                            ),
                            entry.getKey()
                    )
            );
        }

        player.sendMessage(component);

        return true;
    }
}
