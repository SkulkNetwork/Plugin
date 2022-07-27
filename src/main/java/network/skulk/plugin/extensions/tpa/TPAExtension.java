package network.skulk.plugin.extensions.tpa;

import network.skulk.plugin.SkulkNetworkPlugin;
import network.skulk.plugin.extensions.Extension;
import network.skulk.plugin.extensions.tpa.commands.TPAAcceptCommand;
import network.skulk.plugin.extensions.tpa.commands.TPACancelCommand;
import network.skulk.plugin.extensions.tpa.commands.TPACommand;
import network.skulk.plugin.extensions.tpa.commands.TPARejectCommand;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;

public final class TPAExtension implements Extension {
    // K List<V>, V teleports to K.
    public final HashMap<String, @Nullable HashSet<String>> tpaRequests = new HashMap<>();

//    // If "*" is in the HashSet, the player ignores everyone.
//    public final HashMap<String, @Nullable HashSet<String>> tpaIgnores = new HashMap<>();

    private final SkulkNetworkPlugin plugin;

    public TPAExtension(SkulkNetworkPlugin mainPlugin) {
        plugin = mainPlugin;
    }

    public void onEnable() {
        try {
            new TPAAcceptCommand(this);
            new TPACancelCommand(this);
            new TPACommand(this);
            new TPARejectCommand(this);
        } catch (Exception error) {
            plugin.reportError("loading the commands for TPAExtension", error);
        }
    }

    public void onDisable() {
//        try {
//
//        } catch (Exception error) {
//            plugin.reportError("", error);
//        }
    }

    public void register(@NotNull String name, @NotNull CommandExecutor executor) {
        plugin.register(name, executor);
    }

    public void runTaskLater(long delay, @NotNull Runnable runnable) {
        plugin.runTaskLater(delay, runnable);
    }

    public @NotNull NamespacedKey makeKey(String key) {
        return plugin.makeKey(key);
    }
}
