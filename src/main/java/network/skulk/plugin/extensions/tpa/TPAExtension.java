package network.skulk.plugin.extensions.tpa;

import network.skulk.plugin.SkulkNetworkPlugin;
import network.skulk.plugin.extensions.tpa.commands.*;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;

public final class TPAExtension {
    // K List<V>, V teleports to K.
    public final HashMap<String, @Nullable HashSet<String>> tpaRequests = new HashMap<>();

    // If "*" is in the HashSet, the player ignores everyone.
    public final HashMap<String, @Nullable HashSet<String>> tpaIgnores = new HashMap<>();

    private final SkulkNetworkPlugin plugin;

    public TPAExtension(SkulkNetworkPlugin mainPlugin) {
        plugin = mainPlugin;

        new TPAAcceptCommand(this);
        new TPACancelCommand(this);
        new TPACommand(this);
        new TPARejectCommand(this);
    }

    public void register(String name, CommandExecutor executor) {
        plugin.register(name, executor);
    }

    public void runTaskLater(long delay, Runnable runnable) {
        plugin.runTaskLater(delay, runnable);
    }
}
