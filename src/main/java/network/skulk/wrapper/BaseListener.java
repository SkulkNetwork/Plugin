package network.skulk.wrapper;

import org.bukkit.event.Listener;

public abstract class BaseListener<E extends BaseExtension> implements Listener {
    private E extension;

    public final void create(final E extension) {
        this.extension = extension;
        this.extension.registerListener(this);
    }

    protected final E getExtension() {
        return this.extension;
    }

    // Utility functions.

    protected final void runAfter(final long delay, final Runnable runnable) {
        this.extension.runAfter(delay, runnable);
    }

    protected final void runAsync(final Runnable runnable) {
        this.extension.runAsync(runnable);
    }
}
