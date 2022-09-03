package network.skulk.wrapper;

import org.bukkit.event.Listener;

import java.io.File;

public class BaseListener<E extends BaseExtension> implements Listener {
    public E extension;

    public final void create(final E extension) {
        this.extension = extension;
        this.extension.registerListener(this);
    }

    public final File getDataFolder() {
        return this.extension.getDataFolder();
    }

    public final void runAfter(final long delay, final Runnable runnable) {
        this.extension.runAfter(delay, runnable);
    }

    public final void runAsync(final Runnable runnable) {
        this.extension.runAsync(runnable);
    }
}
