package network.skulk.pluginrewrite.extensions.tpa;

import com.google.common.collect.HashMultimap;
import network.skulk.wrapper.BaseExtension;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

public final class TPAExtension extends BaseExtension {
    public final HashMultimap<String, String> tpaRequests = HashMultimap.create();
    public final File tpaIgnoresFile = new File(this.getDataFolder(), "tpaIgnores.yml");
    public HashMultimap<String, String> tpaIgnores;

    @Override
    public void onEnableHook() throws Exception {
        this.tpaIgnores = new Yaml().load(new FileInputStream(this.tpaIgnoresFile));
    }

    @Override
    public void onDisableHook() throws Exception {
        new Yaml().dump(tpaIgnores, new PrintWriter(this.tpaIgnoresFile));
    }
}
