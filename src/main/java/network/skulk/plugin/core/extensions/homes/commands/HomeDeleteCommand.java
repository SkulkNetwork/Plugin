package network.skulk.plugin.core.extensions.homes.commands;

import network.skulk.plugin.core.extensions.homes.HomesExtension;
import network.skulk.plugin.helpers.MiniMessageHelper;
import network.skulk.plugin.wrapper.BaseCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class HomeDeleteCommand extends BaseCommand<HomesExtension> {
    public HomeDeleteCommand(final @NotNull HomesExtension extension) {
        super(extension);
    }

    @Override protected void init() {
        this.name = "home-delete";
        this.playerOnly = true;
        this.maxArgs = 1;
        this.minArgs = 0;
    }

    @Override protected boolean execute(final @NotNull Player player, final @NotNull String[] args) {
        final var playerHomes = this.getExtension().getHomes().get(player.getUniqueId());

        if (args.length == 0) {
            if (playerHomes.containsKey("home")) {
                playerHomes.remove("home");
                MiniMessageHelper.sendMessage(player, "green", '✓', "Successfully deleted your default home (default homes are named 'home').");
                return true;
            }

            MiniMessageHelper.sendMessage(player, "red", '!', "Can't delete your default home because you don't have a default home (default homes are named 'home').");
            return true;
        }

        final var homeName = args[0];

        if (!playerHomes.containsKey(homeName)) {
            MiniMessageHelper.sendMessage(player, "red", '!', "You don't have a home named <b><0></b>.", homeName);
            return true;
        }

        playerHomes.remove(homeName);
        MiniMessageHelper.sendMessage(player, "green", '✓', "Successfully deleted the home named <b><0></b>.", homeName);

        return true;
    }
}
