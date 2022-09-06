package network.skulk.plugin.extensions.homes;

import com.google.common.collect.Multimap;
import network.skulk.plugin.extensions.homes.commands.HomeCommand;
import network.skulk.plugin.extensions.homes.commands.HomeDeleteCommand;
import network.skulk.plugin.extensions.homes.commands.HomeListCommand;
import network.skulk.plugin.extensions.homes.commands.HomeSetCommand;
import network.skulk.wrapper.BaseExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import static network.skulk.utils.Singletons.getYaml;

// TODO: MAYBE? check player on death and respawn them in their home.
public final class HomesExtension extends BaseExtension {
    private final File homesFile = new File(this.getDataFolder(), "homes.yml");
    private Multimap<String, Home> homes;

    @Override
    protected void initCommands() {
        new HomeCommand().create(this);
        new HomeDeleteCommand().create(this);
        new HomeListCommand().create(this);
        new HomeSetCommand().create(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void onEnableHook() throws Exception {
        final var yaml = getYaml();

        this.homes = yaml.load(new FileInputStream(this.homesFile));

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
