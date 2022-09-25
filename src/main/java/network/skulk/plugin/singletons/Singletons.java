package network.skulk.plugin.singletons;

import network.skulk.plugin.pdts.BooleanPersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

public abstract class Singletons {
    private static final Yaml yaml = new Yaml();
    private static final BooleanPersistentDataType booleanPersistentDataType = new BooleanPersistentDataType();

    public static @NotNull Yaml getYaml() {
        return Singletons.yaml;
    }

    public static @NotNull BooleanPersistentDataType getBooleanPersistentDataType() {
        return Singletons.booleanPersistentDataType;
    }
}
