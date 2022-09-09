package network.skulk.utils;

import network.skulk.pdts.BooleanPDT;
import org.yaml.snakeyaml.Yaml;

public abstract class Singletons {
    private static final Yaml yaml = new Yaml();
    private static final BooleanPDT booleanPdt = new BooleanPDT();

    public static Yaml getYaml() {
        return yaml;
    }

    public static BooleanPDT getBooleanPdt() {
        return booleanPdt;
    }
}
