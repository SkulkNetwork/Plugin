package tk.skulk.plugin.core.extensions.homes.commands;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.extensions.homes.HomesExtension;
import tk.skulk.plugin.utils.Location;
import tk.skulk.plugin.wrapper.BaseCommand;

import static tk.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

public final class HomeSetCommand extends BaseCommand<HomesExtension> {
    public HomeSetCommand(final @NotNull HomesExtension extension) {
        super(extension);
    }

    @Override
    protected void init() {
        this.setName("home-set");
        this.setDescription("Makes the current location a home.");
        this.setUsage("/home-set [name]");
        this.setMaxArgs(1);
    }

    @Override
    protected boolean execute(final @NotNull Player player, final @NotNull String[] args) {
        final var playerHomes = this.getExtension().getHomes().get(player.getUniqueId());

        final String homeName;

        if (args.length == 0) {
            if (playerHomes.containsKey("home")) {
                // Player already has a home named 'home'
                sendMessage(
                    player,
                    "red",
                    '!',
                    "You already have a default home. You need to delete that home and set it again."
                );
                return true;
            }

            homeName = "home";
        }
        else {
            homeName = args[0];
        }


        if (playerHomes.containsKey(homeName)) {
            sendMessage(player, "red", '!', "You already have a home named <b><0></b>.", homeName);
            return true;
        }

        if (homeName.length() > 16) {
            sendMessage(player, "red", '!', "Home names can't be longer than 16 characters.");
            return true;
        }

        if (playerHomes.size() >= 16) {
            sendMessage(player, "red", '!', "You have the maximum amount of homes allowed (16).");
            return true;
        }

        playerHomes.put(homeName, new Location(player.getLocation()));

        sendMessage(
            player,
            "green",
            '✓',
            "Successfully created a home named <b><0></b>.",
            homeName
        );

        return true;
    }
}
