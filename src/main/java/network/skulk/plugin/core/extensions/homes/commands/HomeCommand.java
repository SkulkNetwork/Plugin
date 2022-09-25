package network.skulk.plugin.core.extensions.homes.commands;

import network.skulk.plugin.core.extensions.homes.HomesExtension;
import network.skulk.plugin.helpers.EffectHelper;
import network.skulk.plugin.helpers.MiniMessageHelper;
import network.skulk.plugin.utils.ShortLocation;
import network.skulk.plugin.wrapper.BaseCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

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
            query = args[0].toLowerCase();
        }

        final var results = new ArrayList<String>();

        for (final String homeName : this.getExtension().getHomes().get(player.getUniqueId()).keySet()) {
            if (homeName.toLowerCase().contains(query)) {
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
            MiniMessageHelper.sendMessage(player, "red", '!', "You don't have any homes.");
            return true;
        }
        else if (args.length == 0 && playerHomesSize == 1) {
            // Gets the one and only home.
            final var home = playerHomes.firstEntry();

            homeLocation = home.getValue();
            homeName = home.getKey();
        }
        else if (args.length == 0) {
            // There are multiple homes and player didn't specify.
            homeLocation = playerHomes.get("home");

            // Player does not have home named "home", which is the default
            if (homeLocation == null) {
                MiniMessageHelper.sendMessage(player, "red", '!', "Please specify a valid home name. You can also name one of your homes '<b>home</b>' to use it without entering a home name.");
                return true;
            }

            homeName = "home";
        }
        else {
            // Player specified a home.
            homeName = args[0];
            homeLocation = playerHomes.get(homeName);

            if (homeLocation == null) {
                MiniMessageHelper.sendMessage(player, "red", '!', "You have no home named <b><0></b>.", homeName);
                return true;
            }
        }

        MiniMessageHelper.sendMessage(player, "green", '✓', "Teleporting you to <b><0></b>...", homeName);
        EffectHelper.playTeleport(player);
        player.teleport(homeLocation.toLocation());

        return true;
    }
}
