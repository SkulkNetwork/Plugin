package network.skulk.pluginrewrite.extensions.tpa;

import com.google.common.collect.HashMultimap;
import network.skulk.pluginrewrite.extensions.tpa.commands.TPAAcceptCommand;
import network.skulk.pluginrewrite.extensions.tpa.commands.TPACommand;
import network.skulk.pluginrewrite.extensions.tpa.commands.TPAListIgnoredCommand;
import network.skulk.pluginrewrite.extensions.tpa.commands.TPARejectCommand;
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
    private final HashMap<String, HashMap<String, BukkitTask>> tpaRequestCancelTasks = new HashMap<>();
    //                  X^^^^^^^         Y^^^^^^: X wants to TPA to Y.
    // Basically outgoing.

    private final File tpaIgnoresFile = new File(this.getDataFolder(), "tpaIgnores.yml");
    // V's must be lowercase.
    private HashMultimap<String, String> tpaIgnores;

    @Override
    protected void initCommands() {
        new TPAAcceptCommand().create(this);
        new TPACommand().create(this);
        new TPAListIgnoredCommand().create(this);
        new TPARejectCommand().create(this);
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
