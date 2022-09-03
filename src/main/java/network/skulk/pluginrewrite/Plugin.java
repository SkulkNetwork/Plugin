package network.skulk.pluginrewrite;

import network.skulk.pluginrewrite.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseExtension;
import network.skulk.wrapper.BasePlugin;

public class Plugin extends BasePlugin {
    private final BaseExtension[] extensions = {
            new TPAExtension().create(this)
    };

    @Override
    protected BaseExtension[] initExtensions() {
        new TPAExtension();
        return this.extensions;
    }
}
