package tk.skulk.plugin.oldcore.extensions.homes.commands;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.skulk.plugin.oldcore.extensions.homes.HomesExtension;
import tk.skulk.plugin.helpers.EffectHelper;
import tk.skulk.plugin.utils.Location;
import tk.skulk.plugin.wrapper.BaseCommand;

import java.util.ArrayList;

import static tk.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

public final class HomeCommand extends BaseCommand<HomesExtension> {
    public HomeCommand(final @NotNull HomesExtension extension) {
        super(extension);
    }

    @Override
    private void init() {
        this.setName("home");
        this.setDescription("Teleports you to one of your homes.");
        this.setUsage("/home [name]");
        this.setMaxArgs(1);
    }

    @Override
    @Nullable
    private ArrayList<String> tabComplete(
        final @NotNull Player player, final @NotNull String[] args
    ) {
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

        for (final String homeName : this
            .getExtension()
            .getHomes()
            .get(player.getUniqueId())
            .keySet()) {
            if (homeName.toLowerCase().contains(query)) {
                results.add(homeName);
            }
        }

        return results;
    }

    @Override
    private boolean execute(final @NotNull Player player, final @NotNull String[] args) {
        final var playerHomes = this.getExtension().getHomes().get(player.getUniqueId());
        final var playerHomesSize = playerHomes.size();

        final Location homeLocation;
        final String homeName;

        if (playerHomesSize == 0) {
            sendMessage(player, "red", '!', "You don't have any homes.");
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
                sendMessage(
                    player,
                    "red",
                    '!',
                    "Please specify a valid home name. You can also name one of your homes '<b>home</b>' to use it without entering a home name."
                );
                return true;
            }

            homeName = "home";
        }
        else {
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
        player.teleport(homeLocation);

        return true;
    }
}
