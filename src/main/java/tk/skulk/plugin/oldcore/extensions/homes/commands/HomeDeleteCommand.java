package tk.skulk.plugin.oldcore.extensions.homes.commands;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.oldcore.extensions.homes.HomesExtension;
import tk.skulk.plugin.wrapper.BaseCommand;

import static tk.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

public final class HomeDeleteCommand extends BaseCommand<HomesExtension> {
    public HomeDeleteCommand(final @NotNull HomesExtension extension) {
        super(extension);
    }

    @Override
    private void init() {
        this.setName("home-delete");
        this.setDescription("Deletes a home.");
        this.setUsage("/home-delete [name]");
        this.setMaxArgs(1);
    }

    @Override
    private boolean execute(final @NotNull Player player, final @NotNull String[] args) {
        final var playerHomes = this.getExtension().getHomes().get(player.getUniqueId());

        if (args.length == 0) {
            if (playerHomes.containsKey("home")) {
                playerHomes.remove("home");
                sendMessage(player,
                    "green",
                    '✓',
                    "Successfully deleted your default home (Default homes are named 'home')."
                );
                return true;
            }

            sendMessage(player,
                "red",
                '!',
                "Can't delete your default home because you don't have a default home (Default homes are named 'home')."
            );
            return true;
        }

        final var homeName = args[0];

        if (!playerHomes.containsKey(homeName)) {
            sendMessage(player, "red", '!', "You don't have a home named <b><0></b>.", homeName);
            return true;
        }

        playerHomes.remove(homeName);
        sendMessage(player,
            "green",
            '✓',
            "Successfully deleted the home named <b><0></b>.",
            homeName
        );

        return true;
    }
}
