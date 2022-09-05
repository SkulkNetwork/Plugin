package network.skulk.pluginrewrite.extensions.tpa;

import com.google.common.collect.HashMultimap;
import network.skulk.pluginrewrite.extensions.tpa.commands.TPACommand;
import network.skulk.wrapper.BaseExtension;
import org.bukkit.scheduler.BukkitTask;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;

public final class TPAExtension extends BaseExtension {
    // Vs want to TPA to K.
    private final HashMultimap<String, String> tpaRequests = HashMultimap.create();
    // When V:K cancels their TPA request to V, the task in V:K gets cancelled and removed from the key.
    private final HashMap<String, HashMap<String, BukkitTask>> tpaRequestCancelTasks = new HashMap<>();

    private final File tpaIgnoresFile = new File(this.getDataFolder(), "tpaIgnores.yml");
    private HashMultimap<String, String> tpaIgnores;

    @Override
    protected void initCommands() {
        new TPACommand().create(this);
    }

    @Override
    public void onEnableHook() throws Exception {
        this.tpaIgnores = new Yaml().load(new FileInputStream(this.tpaIgnoresFile));
    }

    @Override
    public void onDisableHook() throws Exception {
        new Yaml().dump(tpaIgnores, new PrintWriter(this.tpaIgnoresFile));
    }

    // Getters.
    public HashMultimap<String, String> getTpaRequests() {
        return this.tpaRequests;
    }

    public HashMap<String, HashMap<String, BukkitTask>> getTpaRequestCancelTasks() {
        return this.tpaRequestCancelTasks;
    }

    public HashMultimap<String, String> getTpaIgnores() {
        return this.tpaIgnores;
    }
}
