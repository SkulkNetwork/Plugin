package network.skulk.plugin.core.extensions.foodbehaviour;

import network.skulk.plugin.core.Plugin;
import network.skulk.plugin.core.extensions.foodbehaviour.listeners.CookieResetInsomniaListener;
import network.skulk.plugin.wrapper.BaseExtension;
import org.jetbrains.annotations.NotNull;

public final class FoodBehaviourExtension extends BaseExtension {
    public FoodBehaviourExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @Override protected void initListeners() {
        new CookieResetInsomniaListener(this);
    }
}
