package network.skulk.helpers;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public abstract class FileHelper {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void createFile(final @NotNull File file) throws Exception {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }
}
