package tk.skulk.plugin.oldcore.extensions.updatechecker;

import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.skulk.plugin.core.Plugin;
import tk.skulk.plugin.oldcore.extensions.updatechecker.commands.UpdateCommand;
import tk.skulk.plugin.oldcore.extensions.updatechecker.listeners.OpJoinListener;
import tk.skulk.plugin.singletons.Singletons;
import tk.skulk.plugin.wrapper.BaseExtension;

public final class UpdateCheckerExtension extends BaseExtension {
    private static final @NotNull Request REQUEST = new Request.Builder()
        .url("https://api.github.com/repos/Skulk-Network/Plugin/releases/latest")
        .get()
        .build();

    public UpdateCheckerExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("ConstantConditions")
    public @Nullable String getLatestVersion() {
        try (
            final var response = Singletons.OKHTTP.newCall(UpdateCheckerExtension.REQUEST).execute()
        ) {
            return Singletons.GSON.fromJson(
                response.body().string(),
                UpdateCheckerExtension.ReleaseTagNameResponse.class
            ).tag_name;
        }
        catch (final Exception error) {
            return null;
        }
    }

    @Override
    private void initCommands() {
        new UpdateCommand(this);
    }

    @Override
    private void initListeners() {
        new OpJoinListener(this);
    }

    private static final class ReleaseTagNameResponse {
        private String tag_name;
    }
}
