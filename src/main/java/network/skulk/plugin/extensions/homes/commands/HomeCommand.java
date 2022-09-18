package network.skulk.plugin.extensions.homes.commands;

import network.skulk.helpers.EffectHelper;
import network.skulk.plugin.extensions.homes.HomesExtension;
import network.skulk.utils.ShortLocation;
import network.skulk.wrapper.BaseCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static network.skulk.helpers.MiniMessageHelper.sendMessage;

public final class HomeCommand extends BaseCommand<HomesExtension> {
    public HomeCommand(final @NotNull HomesExtension extension) {
        super(extension);
    }

    @Override public void init() {
        this.name = "home";
        this.playerOnly = true;
        this.maxArgs = 1;
        this.minArgs = 0;
    }

    @Override protected @Nullable ArrayList<String> tabComplete(final @NotNull Player player, final @NotNull String[] args) {
        if (args.length > 1) {
            return null;
        }

        final String query;

        if (args.length == 0) {
            query = "";

        }
        else {
            query = args[0];
        }

        final var results = new ArrayList<String>();

        for (final String homeName : this.getExtension().getHomes().get(player.getUniqueId()).keySet()) {
            if (homeName.toLowerCase().contains(query.toLowerCase())) {
                results.add(homeName);
            }
        }

        return results;
    }

    @Override protected boolean execute(final @NotNull Player player, final @NotNull String[] args) {
        final var playerHomes = this.getExtension().getHomes().get(player.getUniqueId());
        final var playerHomesSize = playerHomes.size();

        final ShortLocation homeLocation;
        final String homeName;

        if (playerHomesSize == 0) {
            sendMessage(player, "red", '!', "You don't have any homes.");
            return true;

        } else if (playerHomesSize == 1 && args.length == 0) {
            // Gets the one and only home.
            final var home = playerHomes.firstEntry();
            homeLocation = home.getValue();
            homeName = home.getKey();

        } else if (args.length == 0) {
            // There are multiple homes and player didn't specify.
            homeLocation = playerHomes.get("home");
            homeName = "home";

            // Player does not have home named "home", which is the default
            if (homeLocation == null) {
                sendMessage(player, "red", '!', "Please specify a valid home name. You can also name one of your homes '<b>home</b>' to use it without entering a home name.");
                return true;
            }

        } else {
            // Player specified a home.
            homeName = args[0];

            homeLocation = playerHomes.get(homeName);

            if (homeLocation == null) {
                sendMessage(player, "red", '!', "You have no home named <b><0></b>.", homeName);
                return true;
            }
        }

        sendMessage(player, "green", '✓', "Teleporting you to <b><0></b>...", homeName);
        EffectHelper.playTeleport(player);
        player.teleport(homeLocation.toLocation());

        return true;
    }
}
