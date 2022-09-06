package network.skulk.plugin.extensions.homes;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import network.skulk.plugin.extensions.homes.commands.HomeCommand;
import network.skulk.plugin.extensions.homes.commands.HomeDeleteCommand;
import network.skulk.plugin.extensions.homes.commands.HomeListCommand;
import network.skulk.plugin.extensions.homes.commands.HomeSetCommand;
import network.skulk.plugin.extensions.homes.listeners.PlayerDeathListener;
import network.skulk.wrapper.BaseExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static network.skulk.utils.FileHelper.createFile;
import static network.skulk.utils.Singletons.getYaml;

// TODO: MAYBE? check player on death and respawn them in their home.
public final class HomesExtension extends BaseExtension {
    private File homesFile;
    private Multimap<String, Home> homes;

    @Override
    protected void initCommands() {
        new HomeCommand().init(this);
        new HomeDeleteCommand().init(this);
        new HomeListCommand().init(this);
        new HomeSetCommand().init(this);
    }

    @Override
    protected void initListeners() {
        new PlayerDeathListener().init(this);
    }

    @Override
    protected void onEnableHook() throws IOException {
        this.homesFile = new File(this.getDataFolder(), "homes.yml");

        createFile(this.homesFile);

        final var yaml = getYaml();

        this.homes = yaml.load(new FileInputStream(this.homesFile));

        if (this.homes == null) {
            this.homes = HashMultimap.create();
        }

        this.runRepeatingAsync(30 * 60 * 20, () -> {
            try {
                yaml.dump(this.homes, new PrintWriter(this.homesFile));
            } catch (final Exception error) {
                this.reportError("There was an error while trying to save homes.\nHere is the traceback:", error);
            }
        });
    }

    @Override
    protected void onDisableHook() throws Exception {
        getYaml().dump(this.homes, new PrintWriter(this.homesFile));
    }

    // Getters.
    public Multimap<String, Home> getHomes() {
        return this.homes;
    }
}
