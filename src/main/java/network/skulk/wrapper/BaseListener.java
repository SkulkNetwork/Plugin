package network.skulk.wrapper;

import org.bukkit.event.Listener;

public abstract class BaseListener<E extends BaseExtension> implements Listener {
    private final E extension;

    protected final E getExtension() {
        return this.extension;
    }

    public BaseListener(final E extension) {
        this.extension = extension;
        this.extension.getPlugin().registerListener(this);
    }
}
