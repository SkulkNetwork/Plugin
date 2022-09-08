package network.skulk.wrapper;

import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public abstract class BaseExtension {
    private final BasePlugin plugin;

    public BaseExtension(final BasePlugin plugin) {
        this.plugin = plugin;
        this.plugin.registerExtension(this);
    }

    public final BasePlugin getPlugin() {
        return this.plugin;
    }


    @OverrideOnly
    protected void initCommands() throws Exception {
    }

    @OverrideOnly
    protected void initListeners() throws Exception {
    }

    @OverrideOnly
    protected void onEnableHook() throws Exception {
    }

    @OverrideOnly
    protected void onDisableHook() throws Exception {
    }

    // Loading mechanic.
    public final void onEnable() throws Exception {
        this.initCommands();
        this.initListeners();
        this.onEnableHook();
    }

    public final void onDisable() throws Exception {
        this.onDisableHook();
    }
}
