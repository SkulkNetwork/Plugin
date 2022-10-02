package network.skulk.plugin.core.extensions.updatechecker.listeners;

import network.skulk.plugin.core.extensions.updatechecker.UpdateCheckerExtension;
import network.skulk.plugin.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import static network.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

public final class OpJoinListener extends BaseListener<UpdateCheckerExtension> {
    public OpJoinListener(final @NotNull UpdateCheckerExtension extension) {
        super(extension);
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(final @NotNull PlayerJoinEvent event) {
        final var player = event.getPlayer();

        if (!player.isOp()) {
            return;
        }

        final var extension = this.getExtension();

        final var currentVersion = extension.getPlugin().getDescription().getVersion();
        final var latestVersion = extension.getLatestVersion();

        if (currentVersion.equalsIgnoreCase(latestVersion)) {
            return;
        }

        sendMessage(player, "orange", '!', "There is a new version available for the plugin: <0>", latestVersion);
    }
}
