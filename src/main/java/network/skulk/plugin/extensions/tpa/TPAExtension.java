package network.skulk.plugin.extensions.tpa;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.extensions.BaseExtension;
import network.skulk.plugin.extensions.tpa.commands.*;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;


public final class TPAExtension implements BaseExtension {
    // K HashSet<V>, V's want to teleport to K.
    public final HashMap<String, @Nullable HashSet<String>> tpaRequests = new HashMap<>();

    public static final String IGNORE_ALL_SYMBOL = "*";
    public final NamespacedKey TPA_IGNORES_KEY;

    public final Plugin plugin;

    public TPAExtension(Plugin plugin) {
        this.plugin = plugin;
        TPA_IGNORES_KEY = new NamespacedKey(plugin, "tpaIgnores");
    }

    public void onEnable() {
        new TPAAcceptCommand(this);
        new TPACancelCommand(this);
        new TPACommand(this);
        new TPAIgnoreAllCommand(this);
        new TPAIgnoreCommand(this);
        new TPAListIgnoredCommand(this);
        new TPARejectCommand(this);
    }

    public void onDisable() {

    }
}
