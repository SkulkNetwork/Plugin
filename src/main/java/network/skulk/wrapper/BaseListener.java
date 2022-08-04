package network.skulk.wrapper;

import org.bukkit.event.Listener;

public class BaseListener<E extends BaseExtension> implements Listener {
    protected final E extension;

    public BaseListener(final E extension) {
        this.extension = extension;
    }
}
