package network.skulk.plugin.core.extensions.homes;

import network.skulk.plugin.core.Plugin;
import network.skulk.plugin.core.extensions.homes.commands.HomeCommand;
import network.skulk.plugin.core.extensions.homes.commands.HomeDeleteCommand;
import network.skulk.plugin.core.extensions.homes.commands.HomeListCommand;
import network.skulk.plugin.core.extensions.homes.commands.HomeSetCommand;
import network.skulk.plugin.core.extensions.homes.listeners.RespawnOnHomeListener;
import network.skulk.plugin.helpers.FileHelper;
import network.skulk.plugin.singletons.Singletons;
import network.skulk.plugin.utils.Location;
import network.skulk.plugin.utils.UUID2CaseInsensitiveMap;
import network.skulk.plugin.wrapper.BaseExtension;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

public final class HomesExtension extends BaseExtension {
    private final File homesFile = new File(this.getPlugin().getDataFolder(), "homes.yml");
    private FileWriter homesFileWriter;
    private UUID2CaseInsensitiveMap<Location> homes = new UUID2CaseInsensitiveMap<>();

    @Override protected void initCommands() {
        new HomeCommand(this);
        new HomeDeleteCommand(this);
        new HomeListCommand(this);
        new HomeSetCommand(this);
    }

    @Override protected void initListeners() {
        new RespawnOnHomeListener(this);
    }

    public HomesExtension(final Plugin plugin) {
        super(plugin);
    }

    @Override protected void onEnableHook() throws Exception {
        final var plugin = this.getPlugin();
        final var yaml = Singletons.getYaml();

        FileHelper.createFile(this.homesFile);
        this.homesFileWriter = new FileWriter(homesFile);

        plugin.runRepeatingAsync(30 * 60 * 20, () -> {
            try {
                yaml.dump(this.homes, this.homesFileWriter);
            } catch (final Exception error) {
                plugin.reportError("There was an error while trying to save homes:", error);
            }
        });

        this.homes = yaml.load(new FileInputStream(this.homesFile));

        if (this.homes == null) {
            this.homes = new UUID2CaseInsensitiveMap<>();
        }
    }

    @Override protected void onDisableHook() {
        Singletons.getYaml().dump(this.homes, this.homesFileWriter);
    }

    // Getters.
    public @NotNull UUID2CaseInsensitiveMap<Location> getHomes() {
        return this.homes;
    }
}
