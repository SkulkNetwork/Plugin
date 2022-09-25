package network.skulk.plugin.extensions.foodbehaviour;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.extensions.foodbehaviour.listeners.CookieResetInsomniaListener;
import network.skulk.wrapper.BaseExtension;

public final class FoodBehaviourExtension extends BaseExtension {
    public FoodBehaviourExtension(final Plugin plugin) {
        super(plugin);
    }

    @Override public void initListeners() {
        new CookieResetInsomniaListener(this);
    }
}
