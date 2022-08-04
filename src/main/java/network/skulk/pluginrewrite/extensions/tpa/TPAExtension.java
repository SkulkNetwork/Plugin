package network.skulk.pluginrewrite.extensions.tpa;

import com.google.common.collect.HashMultimap;
import network.skulk.wrapper.BaseExtension;
import network.skulk.wrapper.BasePlugin;

import java.io.File;

public final class TPAExtension extends BaseExtension {
    public final HashMultimap<String, String> tpaRequests = HashMultimap.create();
    public final HashMultimap<String, String> tpaIgnores = HashMultimap.create();

    public TPAExtension(final BasePlugin plugin) {
        super(plugin);
    }

    @Override
    public void onEnable() throws Exception {
        final File dataFolder = getDataFolder();


    }
}
