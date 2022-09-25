package network.skulk.plugin.singletons;

import network.skulk.plugin.pdts.BooleanPersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

public abstract class Singletons {
    private static final Yaml YAML_INSTANCE = new Yaml();
    private static final BooleanPersistentDataType BOOLEAN_PERSISTENT_DATA_TYPE_INSTANCE = new BooleanPersistentDataType();

    public static @NotNull Yaml getYaml() {
        return Singletons.YAML_INSTANCE;
    }

    public static @NotNull BooleanPersistentDataType getBooleanPersistentDataType() {
        return Singletons.BOOLEAN_PERSISTENT_DATA_TYPE_INSTANCE;
    }
}
