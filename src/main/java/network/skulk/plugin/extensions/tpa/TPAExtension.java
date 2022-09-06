package network.skulk.plugin.extensions.tpa;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import network.skulk.plugin.extensions.tpa.commands.*;
import network.skulk.plugin.extensions.tpa.listeners.PlayerQuitListener;
import network.skulk.wrapper.BaseExtension;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static network.skulk.utils.FileHelper.createFile;
import static network.skulk.utils.Singletons.getYaml;

// TODO: save the ignores every 30 minutes.
public final class TPAExtension extends BaseExtension {
    // Vs want to TPA to K.
    private final Multimap<String, String> tpaRequests = HashMultimap.create();
    private final Map<String, Map<String, BukkitTask>> tpaRequestCancelTasks = new HashMap<>();

    private File tpaIgnoresFile;

    private Multimap<String, String> tpaIgnores;

    @Override
    protected void initCommands() {
        new TPAAcceptCommand().init(this);
        new TPACancelCommand().init(this);
        new TPACommand().init(this);
        new TPAIgnoreAllCommand().init(this);
        new TPAIgnoreCommand().init(this);
        new TPAListIgnoredCommand().init(this);
        new TPARejectCommand().init(this);
    }

    @Override
    protected void initListeners() {
        new PlayerQuitListener().init(this);
    }

    @Override
    protected void onEnableHook() throws IOException {
        this.tpaIgnoresFile = new File(this.getDataFolder(), "tpaIgnores.yml");

        createFile(this.tpaIgnoresFile);

        final var yaml = getYaml();

        this.tpaIgnores = yaml.load(new FileInputStream(this.tpaIgnoresFile));

        if (this.tpaIgnores == null) {
            this.tpaIgnores = HashMultimap.create();
        }

        this.runRepeatingAsync(30 * 60 * 20, () -> {
            try {
                yaml.dump(this.tpaIgnores, new FileWriter(this.tpaIgnoresFile));
            } catch (final Exception error) {
                this.reportError("There was an error while trying to save the TPA ignores.\nHere is the traceback:", error);
            }
        });
    }

    @Override
    protected void onDisableHook() throws Exception {
        getYaml().dump(this.tpaIgnores, new PrintWriter(this.tpaIgnoresFile));
    }

    // Getters.
    public Multimap<String, String> getTpaRequests() {
        return this.tpaRequests;
    }

    public Map<String, Map<String, BukkitTask>> getTpaRequestCancelTasks() {
        return this.tpaRequestCancelTasks;
    }

    public Multimap<String, String> getTpaIgnores() {
        return this.tpaIgnores;
    }
}
