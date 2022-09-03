package network.skulk.pluginrewrite.extensions.tpa;

import com.google.common.collect.HashMultimap;
import network.skulk.wrapper.BaseExtension;

import java.io.File;

public final class TPAExtension extends BaseExtension {
    public final HashMultimap<String, String> tpaRequests = HashMultimap.create();
    public final HashMultimap<String, String> tpaIgnores = HashMultimap.create();

    @Override
    public void onEnableHook() throws Exception {
        final File dataFolder = getDataFolder();


    }
}
