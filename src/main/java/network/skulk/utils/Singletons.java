package network.skulk.utils;

import org.yaml.snakeyaml.Yaml;

public abstract class Singletons {
    private static final Yaml yaml = new Yaml();

    public static final Yaml getYaml() {
        return Singletons.yaml;
    }
}
