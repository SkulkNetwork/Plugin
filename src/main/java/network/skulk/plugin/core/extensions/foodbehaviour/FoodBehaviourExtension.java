package network.skulk.plugin.core.extensions.foodbehaviour;

import network.skulk.plugin.core.Plugin;
import network.skulk.plugin.core.extensions.foodbehaviour.listeners.CookieResetInsomniaListener;
import network.skulk.plugin.wrapper.BaseExtension;

public final class FoodBehaviourExtension extends BaseExtension {
    public FoodBehaviourExtension(final Plugin plugin) {
        super(plugin);
    }

    @Override protected void initListeners() {
        new CookieResetInsomniaListener(this);
    }
}
