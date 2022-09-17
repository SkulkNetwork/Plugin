package network.skulk.singletons;

import network.skulk.pdts.BooleanPDT;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

public abstract class Singletons {
    private static final Yaml yaml = new Yaml();
    private static final BooleanPDT booleanPdt = new BooleanPDT();

    public static @NotNull Yaml getYaml() {
        return yaml;
    }

    public static @NotNull BooleanPDT getBooleanPdt() {
        return booleanPdt;
    }
}
