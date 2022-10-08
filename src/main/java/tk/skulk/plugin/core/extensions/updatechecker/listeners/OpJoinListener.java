package tk.skulk.plugin.core.extensions.updatechecker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.extensions.updatechecker.UpdateCheckerExtension;
import tk.skulk.plugin.wrapper.BaseListener;

import static tk.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

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
        final var plugin = extension.getPlugin();

        final var currentVersion = plugin.getDescription().getVersion();

        plugin.runAsync(() -> {
            // Blocking HTTP request.
            final var latestVersion = extension.getLatestVersion();

            if (currentVersion.equalsIgnoreCase(latestVersion)) {
                return;
            }

            plugin.runSync(() ->
                    sendMessage(player, "orange", '!', "There is a new version available for the plugin: <0>", latestVersion)
            );
        });
    }
}
