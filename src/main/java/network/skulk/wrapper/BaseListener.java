package network.skulk.wrapper;

import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

public abstract class BaseListener<E extends BaseExtension> implements Listener {
    private E extension;

    public final void create(final E extension) {
        this.extension = extension;
        this.extension.registerListener(this);
    }

    // Utility functions.

    protected final BukkitTask runAfter(final long delay, final Runnable runnable) {
        return this.extension.runAfter(delay, runnable);
    }

    protected final void runAsync(final Runnable runnable) {
        this.extension.runAsync(runnable);
    }

    // Getters.
    protected final E getExtension() {
        return this.extension;
    }
}
