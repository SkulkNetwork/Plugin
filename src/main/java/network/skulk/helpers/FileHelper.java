package network.skulk.helpers;

import java.io.File;

public abstract class FileHelper {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void createFile(final File file) throws Exception {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }
}
