package network.skulk.plugin.core.extensions.updatechecker;

import network.skulk.plugin.core.Plugin;
import network.skulk.plugin.core.extensions.updatechecker.commands.UpdateCommand;
import network.skulk.plugin.core.extensions.updatechecker.listeners.OpJoinListener;
import network.skulk.plugin.singletons.Singletons;
import network.skulk.plugin.wrapper.BaseExtension;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class UpdateCheckerExtension extends BaseExtension {
    private static final @NotNull Request REQUEST = new Request.Builder()
            .url("https://api.github.com/repos/SkulkNetwork/SkulkPlugin/releases/latest")
            .get()
            .build();

    @SuppressWarnings("ConstantConditions")
    public @Nullable String getLatestVersion() {
        try (final var response = Singletons.OKHTTP.newCall(UpdateCheckerExtension.REQUEST).execute()) {
            return Singletons.GSON.fromJson(response.body().string(), UpdateCheckerExtension.ReleaseTagNameResponse.class).tag_name;
        } catch (final Exception error) {
            return null;
        }
    }

    public UpdateCheckerExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @Override protected void initCommands() {
        new UpdateCommand(this);
    }

    @Override protected void initListeners() {
        new OpJoinListener(this);
    }

    private static final class ReleaseTagNameResponse {
        private String tag_name;
    }
}
