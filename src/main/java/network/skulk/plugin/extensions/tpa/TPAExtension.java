package network.skulk.plugin.extensions.tpa;

import com.google.common.collect.HashMultimap;
import network.skulk.helpers.FileHelper;
import network.skulk.plugin.Plugin;
import network.skulk.plugin.extensions.tpa.commands.*;
import network.skulk.plugin.extensions.tpa.listeners.PlayerQuitListener;
import network.skulk.singletons.Singletons;
import network.skulk.utils.NestedPlayerMap;
import network.skulk.wrapper.BaseExtension;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.UUID;

public final class TPAExtension extends BaseExtension {
    // Vs want to TPA to K.
    private final NestedPlayerMap<BukkitTask> tpaRequests = new NestedPlayerMap<>();
    private final File tpaIgnoresFile = new File(this.getPlugin().getDataFolder(), "tpaIgnores.yml");
    private HashMultimap<@NotNull UUID, @NotNull String> tpaIgnores = HashMultimap.create();
    private FileWriter tpaIgnoresFileWriter;

    @Override protected void initCommands() {
        new TPAAcceptCommand(this);
        new TPACancelCommand(this);
        new TPACommand(this);
        new TPAIgnoreAllCommand(this);
        new TPAIgnoreCommand(this);
        new TPAListIgnoredCommand(this);
        new TPARejectCommand(this);
    }

    @Override protected void initListeners() {
        new PlayerQuitListener(this);
    }

    public TPAExtension(final @NotNull Plugin extension) {
        super(extension);
    }

    @Override protected void onEnableHook() throws Exception {
        final var plugin = this.getPlugin();
        final var yaml = Singletons.getYaml();

        FileHelper.createFile(this.tpaIgnoresFile);
        this.tpaIgnoresFileWriter = new FileWriter(this.tpaIgnoresFile);
        plugin.runRepeatingAsync(30 * 60 * 20, () -> {
            try {
                yaml.dump(this.tpaIgnores, this.tpaIgnoresFileWriter);
            } catch (final Exception error) {
                plugin.reportError("There was an error while trying to save the TPA ignores:", error);
            }
        });

        this.tpaIgnores = yaml.load(new FileInputStream(this.tpaIgnoresFile));

        if (this.tpaIgnores == null) {
            this.tpaIgnores = HashMultimap.create();
        }
    }

    @Override protected void onDisableHook() {
        Singletons.getYaml().dump(this.tpaIgnores, this.tpaIgnoresFileWriter);
    }

    public @NotNull NestedPlayerMap<BukkitTask> getTpaRequests() {
        return this.tpaRequests;
    }

    public @NotNull HashMultimap<@NotNull UUID, @NotNull String> getTpaIgnores() {
        return this.tpaIgnores;
    }
}
