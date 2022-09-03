package network.skulk.pluginrewrite;

import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseExtension;
import network.skulk.wrapper.BasePlugin;

public class Plugin extends BasePlugin {
    @Override
    protected Class<? extends BaseExtension>[] extensions() {
        return new Class<? extends BaseExtension>[]{
                TPAExtension.class
        };
    }
}
