package network.skulk.plugin.extensions.homes;

import com.google.common.collect.Multimap;
import network.skulk.plugin.extensions.homes.commands.HomeCommand;
import network.skulk.wrapper.BaseExtension;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

public final class HomesExtension extends BaseExtension {
    private final File homesFile = new File(this.getDataFolder(), "homes.yml");
    private Multimap<String, Home> homes;

    @Override
    protected void initCommands() {
        new HomeCommand().create(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void onEnableHook() throws Exception {
        this.homes = new Yaml().load(new FileInputStream(this.homesFile));
    }

    @Override
    protected void onDisableHook() throws Exception {
        new Yaml().dump(this.homes, new PrintWriter(this.homesFile));
    }

    // Getters.
    public Multimap<String, Home> getHomes() {
        return this.homes;
    }
}
