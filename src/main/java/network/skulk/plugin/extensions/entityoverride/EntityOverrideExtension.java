package network.skulk.plugin.extensions.entityoverride;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.extensions.entityoverride.listeners.*;
import network.skulk.wrapper.BaseExtension;
import org.jetbrains.annotations.NotNull;

public final class EntityOverrideExtension extends BaseExtension {
    public EntityOverrideExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @Override protected void initListeners() {
        new CookieResetInsomniaListener(this);
        new CreeperNoGriefListener(this);
        new DragonDropElytraListener(this);
        new EndermanNoGriefListener(this);
        new ExplosionDropAllListener(this);
    }
}
