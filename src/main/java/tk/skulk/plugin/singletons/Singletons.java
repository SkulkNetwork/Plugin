package tk.skulk.plugin.singletons;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;
import tk.skulk.plugin.pdts.BooleanPersistentDataType;

public abstract class Singletons {
    public static final @NotNull Yaml YAML = new Yaml();
    public static final @NotNull Gson GSON = new Gson();
    public static final @NotNull BooleanPersistentDataType BOOLEAN_PERSISTENT_DATA_TYPE = new BooleanPersistentDataType();
    public static final @NotNull OkHttpClient OKHTTP = new OkHttpClient();
}
