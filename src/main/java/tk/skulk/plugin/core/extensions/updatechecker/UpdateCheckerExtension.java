package tk.skulk.plugin.core.extensions.updatechecker;

import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.skulk.plugin.core.Plugin;
import tk.skulk.plugin.core.extensions.updatechecker.commands.UpdateCommand;
import tk.skulk.plugin.core.extensions.updatechecker.listeners.OpJoinListener;
import tk.skulk.plugin.singletons.Singletons;
import tk.skulk.plugin.wrapper.BaseExtension;

public final class UpdateCheckerExtension extends BaseExtension {
    private static final @NotNull Request REQUEST = new Request.Builder()
            .url("https://api.github.com/repos/Skulk-Network/Plugin/releases/latest")
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
