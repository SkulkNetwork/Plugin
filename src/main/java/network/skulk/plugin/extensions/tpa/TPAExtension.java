package network.skulk.plugin.extensions.tpa;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import network.skulk.plugin.extensions.tpa.commands.*;
import network.skulk.plugin.extensions.tpa.listeners.PlayerQuitListener;
import network.skulk.wrapper.BaseExtension;
import org.bukkit.scheduler.BukkitTask;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

// TODO: save the ignores every 30 minutes.
public final class TPAExtension extends BaseExtension {
    // Vs want to TPA to K.
    private final Multimap<String, String> tpaRequests = HashMultimap.create();
    private final Map<String, HashMap<String, BukkitTask>> tpaRequestCancelTasks = new HashMap<>();

    private final File tpaIgnoresFile = new File(this.getDataFolder(), "tpaIgnores.yml");

    private Multimap<String, String> tpaIgnores;

    @Override
    protected void initCommands() {
        new TPAAcceptCommand().create(this);
        new TPACancelCommand().create(this);
        new TPACommand().create(this);
        new TPAIgnoreAllCommand().create(this);
        new TPAIgnoreCommand().create(this);
        new TPAListIgnoredCommand().create(this);
        new TPARejectCommand().create(this);
    }

    @Override
    protected void initListeners() {
        new PlayerQuitListener().create(this);
    }

    @Override
    protected void onEnableHook() throws Exception {
        this.tpaIgnores = new Yaml().load(new FileInputStream(this.tpaIgnoresFile));
    }

    @Override
    protected void onDisableHook() throws Exception {
        new Yaml().dump(this.tpaIgnores, new PrintWriter(this.tpaIgnoresFile));
    }

    // Getters.
    public Multimap<String, String> getTpaRequests() {
        return this.tpaRequests;
    }

    public Map<String, HashMap<String, BukkitTask>> getTpaRequestCancelTasks() {
        return this.tpaRequestCancelTasks;
    }

    public Multimap<String, String> getTpaIgnores() {
        return this.tpaIgnores;
    }
}
