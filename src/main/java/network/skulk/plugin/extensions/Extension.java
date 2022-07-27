package network.skulk.plugin.extensions;

public interface Extension {
    void onEnable();

    // TODO: Remove suppressor.
    @SuppressWarnings("EmptyMethod")
    void onDisable();
}
