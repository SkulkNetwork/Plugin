package network.skulk.plugin.core.extensions.tpa;

import com.google.common.collect.HashMultimap;
import network.skulk.plugin.core.Plugin;
import network.skulk.plugin.core.extensions.tpa.commands.TPAAcceptCommand;
import network.skulk.plugin.core.extensions.tpa.commands.TPACancelCommand;
import network.skulk.plugin.core.extensions.tpa.commands.TPACommand;
import network.skulk.plugin.core.extensions.tpa.commands.TPAIgnoreAllCommand;
import network.skulk.plugin.core.extensions.tpa.commands.TPAIgnoreCommand;
import network.skulk.plugin.core.extensions.tpa.commands.TPAListIgnoredCommand;
import network.skulk.plugin.core.extensions.tpa.commands.TPARejectCommand;
import network.skulk.plugin.core.extensions.tpa.listeners.PlayerQuitListener;
import network.skulk.plugin.helpers.FileHelper;
import network.skulk.plugin.singletons.Singletons;
import network.skulk.plugin.utils.NestedPlayerMap;
import network.skulk.plugin.wrapper.BaseExtension;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.UUID;

public final class TPAExtension extends BaseExtension {
    // Vs want to TPA to K.
    private final @NotNull NestedPlayerMap<@NotNull BukkitTask> tpaRequests = new NestedPlayerMap<>();
    private final @NotNull File tpaIgnoresFile = new File(this.getPlugin().getDataFolder(), "tpaIgnores.yml");
    private @NotNull HashMultimap<@NotNull UUID, @NotNull String> tpaIgnores = HashMultimap.create();
    private FileWriter tpaIgnoresFileWriter;

    public TPAExtension(final @NotNull Plugin extension) {
        super(extension);
    }

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

    @Override protected void onEnableHook() throws Exception {
        final var plugin = this.getPlugin();

        FileHelper.createFile(this.tpaIgnoresFile);
        this.tpaIgnoresFileWriter = new FileWriter(this.tpaIgnoresFile);

        plugin.runRepeatingAsync(30 * 60 * 20, () -> {
            try {
                Singletons.YAML.dump(this.tpaIgnores, this.tpaIgnoresFileWriter);
            } catch (final Exception error) {
                plugin.reportError("There was an error while trying to save the TPA ignores:", error);
            }
        });

        this.tpaIgnores = Singletons.YAML.load(new FileInputStream(this.tpaIgnoresFile));

        if (this.tpaIgnores == null) {
            this.tpaIgnores = HashMultimap.create();
        }
    }

    @Override protected void onDisableHook() {
        Singletons.YAML.dump(this.tpaIgnores, this.tpaIgnoresFileWriter);
    }

    public @NotNull NestedPlayerMap<@NotNull BukkitTask> getTpaRequests() {
        return this.tpaRequests;
    }

    public @NotNull HashMultimap<@NotNull UUID, @NotNull String> getTpaIgnores() {
        return this.tpaIgnores;
    }
}
