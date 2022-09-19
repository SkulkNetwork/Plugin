package network.skulk.wrapper;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public abstract class BaseListener<E extends BaseExtension> implements Listener {
    private final E extension;

    public BaseListener(final @NotNull E extension) {
        this.extension = extension;

        Bukkit.getPluginManager().registerEvents(this, extension.getPlugin());
    }

    protected final @NotNull E getExtension() {
        return this.extension;
    }
}
