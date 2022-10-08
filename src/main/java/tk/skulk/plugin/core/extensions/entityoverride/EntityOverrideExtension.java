package tk.skulk.plugin.core.extensions.entityoverride;

import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.Plugin;
import tk.skulk.plugin.core.extensions.entityoverride.listeners.CreeperNoGriefListener;
import tk.skulk.plugin.core.extensions.entityoverride.listeners.DragonDropElytraListener;
import tk.skulk.plugin.core.extensions.entityoverride.listeners.EndermanNoGriefListener;
import tk.skulk.plugin.core.extensions.entityoverride.listeners.ExplosionDropAllListener;
import tk.skulk.plugin.wrapper.BaseExtension;

public final class EntityOverrideExtension extends BaseExtension {
    public EntityOverrideExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @Override protected void initListeners() {
        new CreeperNoGriefListener(this);
        new DragonDropElytraListener(this);
        new EndermanNoGriefListener(this);
        new ExplosionDropAllListener(this);
    }
}
