package network.skulk.plugin.extensions.homes.commands;

import network.skulk.plugin.extensions.homes.Home;
import network.skulk.plugin.extensions.homes.HomesExtension;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.entity.Player;

import static network.skulk.utils.MiniMessageFormat.sendMessage;

public final class HomeDeleteCommand extends BaseCommand<HomesExtension> {
    public HomeDeleteCommand(final HomesExtension extension) {
        super(extension);
    }

    @Override
    protected void init() {
        this.name = "home-delete";
        this.playerOnly = true;
        this.maxArgs = 1;
        this.minArgs = 0;
    }

    @Override
    protected boolean execute(final Player player, final String[] args) {
        final var playerHomes = this.getExtension().getHomes().get(player.getName());

        if (args.length == 0) {
            for (final Home home : playerHomes) {
                final var existingHomeName = home.name();

                if (existingHomeName.equalsIgnoreCase("home")) {
                    playerHomes.remove(home);

                    sendMessage(player, "green", '✓', "Successfully deleted your default home (default homes are named 'home').");

                    return true;
                }

                sendMessage(player, "red", '!', "Can't delete your default home because you don't have a default home (default homes are named 'home').");
                return true;
            }
        }

        final var homeName = args[0];

        for (final Home home : playerHomes) {
            final var existingHomeName = home.name();

            if (existingHomeName.equalsIgnoreCase(homeName)) {
                playerHomes.remove(home);
                sendMessage(player, "green", '✓', "Successfully deleted the home named <b><0></b>.", existingHomeName);
            }
        }

        sendMessage(player, "red", '!', "You don't have a home named <b><0></b>.", homeName);

        return true;
    }
}
