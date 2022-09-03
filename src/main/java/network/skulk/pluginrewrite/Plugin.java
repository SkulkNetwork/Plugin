package network.skulk.pluginrewrite;

import network.skulk.pluginrewrite.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseExtension;
import network.skulk.wrapper.BasePlugin;

public class Plugin extends BasePlugin {

    @Override
    protected BaseExtension[] initExtensions() {
        return new BaseExtension[]{
                new TPAExtension().create(this)
        };
    }
}
