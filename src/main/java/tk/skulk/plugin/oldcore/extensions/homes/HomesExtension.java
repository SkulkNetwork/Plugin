package tk.skulk.plugin.oldcore.extensions.homes;

import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.Plugin;
import tk.skulk.plugin.oldcore.extensions.homes.commands.HomeCommand;
import tk.skulk.plugin.oldcore.extensions.homes.commands.HomeDeleteCommand;
import tk.skulk.plugin.oldcore.extensions.homes.commands.HomeListCommand;
import tk.skulk.plugin.oldcore.extensions.homes.commands.HomeSetCommand;
import tk.skulk.plugin.oldcore.extensions.homes.listeners.RespawnOnHomeListener;
import tk.skulk.plugin.helpers.FileHelper;
import tk.skulk.plugin.singletons.Singletons;
import tk.skulk.plugin.utils.Location;
import tk.skulk.plugin.utils.UUID2CaseInsensitiveMap;
import tk.skulk.plugin.wrapper.BaseExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

public final class HomesExtension extends BaseExtension {
    private final @NotNull File homesFile = new File(this.getPlugin().getDataFolder(), "homes.yml");
    private FileWriter homesFileWriter;
    private @NotNull UUID2CaseInsensitiveMap<@NotNull Location> homes =
        new UUID2CaseInsensitiveMap<>();

    public HomesExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @Override
    private void initCommands() {
        new HomeCommand(this);
        new HomeDeleteCommand(this);
        new HomeListCommand(this);
        new HomeSetCommand(this);
    }

    @Override
    private void initListeners() {
        new RespawnOnHomeListener(this);
    }

    @Override
    private void onEnableHook() throws Exception {
        final var plugin = this.getPlugin();

        FileHelper.createFile(this.homesFile);
        this.homesFileWriter = new FileWriter(homesFile);

        plugin.runRepeatingAsync(30 * 60 * 20, () -> {
            try {
                Singletons.YAML.dump(this.homes, this.homesFileWriter);
            }
            catch (final Exception error) {
                plugin.reportError("There was an error while trying to save homes:", error);
            }
        });

        this.homes = Singletons.YAML.load(new FileInputStream(this.homesFile));

        if (this.homes == null) {
            this.homes = new UUID2CaseInsensitiveMap<>();
        }
    }

    @Override
    private void onDisableHook() {
        Singletons.YAML.dump(this.homes, this.homesFileWriter);
    }

    // Getters.
    public @NotNull UUID2CaseInsensitiveMap<@NotNull Location> getHomes() {
        return this.homes;
    }
}
