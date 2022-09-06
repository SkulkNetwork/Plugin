package network.skulk.plugin.extensions.homes.commands;

import network.skulk.plugin.extensions.homes.Home;
import network.skulk.plugin.extensions.homes.HomesExtension;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static network.skulk.utils.MiniMessageFormat.sendMessage;
import static network.skulk.utils.SoundPlayer.playTeleport;

public final class HomeCommand extends BaseCommand<HomesExtension> {

    @Override
    public void init() {
        this.aliases = new String[]{"home"};
        this.playerOnly = true;
        this.maxArgs = 1;
        this.minArgs = 0;
    }

    @Override
    protected List<String> tabComplete(final Player player, final String[] args) {
        if (args.length > 1) {
            return null;
        }

        final String query;

        if (args.length == 0) {
            query = "";
        } else {
            query = args[0];
        }

        final var results = new ArrayList<String>();

        for (final Home home : this.getExtension().getHomes().get(player.getName())) {
            final var homeName = home.name();
            if (homeName.toLowerCase().contains(query.toLowerCase())) {
                results.add(homeName);
            }
        }

        return results;
    }

    @Override
    protected boolean execute(final Player player, final String[] args) {
        final var playerHomes = this.getExtension().getHomes().get(player.getName());
        final var playerHomesSize = playerHomes.size();

        @Nullable Home home = null;
        final String homeName;

        if (playerHomesSize == 0) {
            sendMessage(player, "red", '!', "You don't have any homes! You can set one by doing <b><gray>/home-set myHomeName</gray></b>");
            return true;

            // Gets the one and only home.
        } else if (playerHomesSize == 1 && args.length == 0) {
            home = playerHomes.iterator().next();

            // There is multiple homes and player didn't specify.
        } else if (args.length == 0) {
            for (final Home pHome : playerHomes) {
                if (pHome.name().equalsIgnoreCase("home")) {
                    home = pHome;
                    break;
                }
            }

            // Player does not have home named "home", which is the default
            if (home == null) {
                sendMessage(player, "red", '!', "Please specify a valid home name. You can also name one of your homes '<b><gray>home</gray></b>' to use it without entering a home name.");
                return true;
            }

            // Player specified a home.
        } else {
            var homeName1 = args[0];

            for (final Home pHome : playerHomes) {
                if (pHome.name().equalsIgnoreCase(homeName1)) {
                    home = pHome;
                    homeName1 = home.name();
                    break;
                }
            }

            if (home == null) {
                sendMessage(player, "red", '!', "You have no home named <b><0></b>.", homeName1);
                return true;
            }
        }
        homeName = home.name();

        sendMessage(player, "green", '✓', "Teleporting you to <b><0></b>...", homeName);
        playTeleport(player);
        player.teleport(home.location());

        return true;
    }
}
