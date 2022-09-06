package network.skulk.utils;

import java.io.File;
import java.io.IOException;

public abstract class FileHelper {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void createFile(final File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }
}
