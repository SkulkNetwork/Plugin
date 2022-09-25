package network.skulk.plugin.core.extensions.homes.commands;

import network.skulk.plugin.core.extensions.homes.HomesExtension;
import network.skulk.plugin.helpers.MiniMessageHelper;
import network.skulk.plugin.utils.Location;
import network.skulk.plugin.wrapper.BaseCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class HomeSetCommand extends BaseCommand<HomesExtension> {
    public HomeSetCommand(final @NotNull HomesExtension extension) {
        super(extension);
    }

    @Override protected void init() {
        this.setName("home-set");
        this.setDescription("Makes the current location a home.");
        this.setUsage("/home-set [name]");
        this.setPlayerOnly(true);
        this.setMaxArgs(1);
        this.setMinArgs(0);
    }

    @Override protected boolean execute(final @NotNull Player player, final @NotNull String[] args) {
        final var playerHomes = this.getExtension().getHomes().get(player.getUniqueId());

        final String homeName;

        if (args.length == 0) {
            if (playerHomes.containsKey("home")) {
                // Player already has a home named 'home'
                MiniMessageHelper.sendMessage(player, "red", '!', "You already have a default home. You need to delete that home and set it again.");
                return true;
            }

            homeName = "home";
        }
        else {
            homeName = args[0];
        }


        if (playerHomes.containsKey(homeName)) {
            MiniMessageHelper.sendMessage(player, "red", '!', "You already have a home named <b><0></b>.", homeName);
            return true;
        }

        if (homeName.length() >= 16) {
            MiniMessageHelper.sendMessage(player, "red", '!', "Home names can't be longer than 16 characters.");
            return true;
        }

        if (playerHomes.size() >= 16) {
            MiniMessageHelper.sendMessage(player, "red", '!', "You have the maximum amount of homes allowed (16).");
            return true;
        }

        playerHomes.put(homeName, new Location(player.getLocation()));

        MiniMessageHelper.sendMessage(player, "green", '✓', "Successfully created a home named <b><0></b>.", homeName);

        return true;
    }
}
