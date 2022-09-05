package network.skulk.pluginrewrite.extensions.tpa;

import com.google.common.collect.HashMultimap;
import network.skulk.wrapper.BaseExtension;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

public final class TPAExtension extends BaseExtension {
    private final HashMultimap<String, String> tpaRequests = HashMultimap.create();
    private final File tpaIgnoresFile = new File(this.getDataFolder(), "tpaIgnores.yml");
    private HashMultimap<String, String> tpaIgnores;

    public HashMultimap<String, String> getTpaRequests() {
        return this.tpaRequests;
    }

    public HashMultimap<String, String> getTpaIgnores() {
        return this.tpaIgnores;
    }

    @Override
    public void onEnableHook() throws Exception {
        this.tpaIgnores = new Yaml().load(new FileInputStream(this.tpaIgnoresFile));
    }

    @Override
    public void onDisableHook() throws Exception {
        new Yaml().dump(tpaIgnores, new PrintWriter(this.tpaIgnoresFile));
    }
}
