package network.skulk.plugin.extensions.homes;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import network.skulk.helpers.FileHelper;
import network.skulk.plugin.Plugin;
import network.skulk.plugin.extensions.homes.commands.HomeCommand;
import network.skulk.plugin.extensions.homes.commands.HomeDeleteCommand;
import network.skulk.plugin.extensions.homes.commands.HomeListCommand;
import network.skulk.plugin.extensions.homes.commands.HomeSetCommand;
import network.skulk.plugin.extensions.homes.listeners.RespawnOnHomeListener;
import network.skulk.singletons.Singletons;
import network.skulk.wrapper.BaseExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

// TODO: MAYBE? check player on death and respawn them in their home.
public final class HomesExtension extends BaseExtension {
    private final File homesFile = new File(this.getPlugin().getDataFolder(), "homes.yml");
    private Multimap<String, Home> homes;

    public HomesExtension(final Plugin plugin) {
        super(plugin);
    }

    @Override
    protected void initCommands() {
        new HomeCommand(this);
        new HomeDeleteCommand(this);
        new HomeListCommand(this);
        new HomeSetCommand(this);
    }

    @Override
    protected void initListeners() {
        new RespawnOnHomeListener(this);
    }

    @Override
    protected void onEnableHook() throws Exception {
        final var plugin = this.getPlugin();
        final var yaml = Singletons.getYaml();

        FileHelper.createFile(this.homesFile);

        this.homes = yaml.load(new FileInputStream(this.homesFile));

        if (this.homes == null) {
            this.homes = HashMultimap.create();
        }

        plugin.runRepeatingAsync(30 * 60 * 20, () -> {
            try {
                yaml.dump(this.homes, new FileWriter(this.homesFile));
            } catch (final Exception error) {
                plugin.reportError("There was an error while trying to save homes. Here is the traceback:", error);
            }
        });
    }

    @Override
    protected void onDisableHook() throws Exception {
        Singletons.getYaml().dump(this.homes, new FileWriter(this.homesFile));
    }

    // Getters.
    public Multimap<String, Home> getHomes() {
        return this.homes;
    }
}
