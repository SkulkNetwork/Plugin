package network.skulk.wrapper;

import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

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

    protected final BukkitTask runAsync(final Runnable runnable) {
        return this.extension.runAsync(runnable);
    }

    public final void runRepeatingAsync(final long interval, final Runnable runnable) {
        this.extension.runRepeatingAsync(interval, runnable);
    }

    protected final void reportError(final String message, @Nullable final Throwable error) {
        this.getExtension().reportError(message, error);
    }

    protected final void reportError(final String message) {
        this.getExtension().reportError(message, null);
    }

    // Getters.
    protected final E getExtension() {
        return this.extension;
    }
}
