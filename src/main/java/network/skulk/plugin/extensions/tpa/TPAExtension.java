package network.skulk.plugin.extensions.tpa;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.extensions.BaseExtension;
import network.skulk.plugin.extensions.tpa.commands.TPACommand;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;


public final class TPAExtension implements BaseExtension {
    // K HashSet<V>, V's want to teleport to K.
    public final HashMap<String, @Nullable HashSet<String>> tpaRequests = new HashMap<>();

    public final Plugin plugin;

    public TPAExtension(Plugin plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        new TPACommand(this);
    }

    public void onDisable() {

    }
}
