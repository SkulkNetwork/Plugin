package network.skulk.plugin.core.extensions.updatechecker;

import network.skulk.plugin.core.Plugin;
import network.skulk.plugin.wrapper.BaseExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public final class UpdateCheckerExtension extends BaseExtension {
    private @Nullable String latestVersion = null;

    public UpdateCheckerExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @Override protected void onEnableHook() throws Exception {
        this.latestVersion = new BufferedReader(
                new InputStreamReader(
                        new URL("https://raw.githubusercontent.com/Skulk-Network/Plugin/build/LATEST.txt")
                                .openConnection()
                                .getInputStream()
                )
        ).readLine();
    }

    public @Nullable String getLatestVersion() {
        return this.latestVersion;
    }
}
