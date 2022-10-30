package tk.skulk.plugin.core.extensions.foodbehaviour;

import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.Plugin;
import tk.skulk.plugin.core.extensions.foodbehaviour.listeners.CookieResetInsomniaListener;
import tk.skulk.plugin.wrapper.BaseExtension;

public final class FoodBehaviourExtension extends BaseExtension {
    public FoodBehaviourExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @Override
    protected void initListeners() {
        new CookieResetInsomniaListener(this);
    }
}
