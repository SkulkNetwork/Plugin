package tk.skulk.plugin.oldcore.extensions.foodbehaviour;

import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.Plugin;
import tk.skulk.plugin.oldcore.extensions.foodbehaviour.listeners.CookieResetInsomniaListener;
import tk.skulk.plugin.wrapper.BaseExtension;

public final class FoodBehaviourExtension extends BaseExtension {
    public FoodBehaviourExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @Override
    private void initListeners() {
        new CookieResetInsomniaListener(this);
    }
}
