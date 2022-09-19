package network.skulk.wrapper;

import org.jetbrains.annotations.NotNull;

import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public abstract class BaseExtension {
    private final BasePlugin plugin;

    public BaseExtension(final @NotNull BasePlugin plugin) {
        this.plugin = plugin;

        this.plugin.getExtensions().add(this);
    }

    public final @NotNull BasePlugin getPlugin() {
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
