package network.skulk.plugin.pdts;

import com.google.errorprone.annotations.DoNotCall;
import network.skulk.plugin.extensions.homes.Home;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public final class HomePDT implements PersistentDataType<byte[], Home> {
    private final String target;

    public HomePDT(String target) {
        this.target = target;
    }

    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    public @NotNull Class<Home> getComplexType() {
        return Home.class;
    }

    @DoNotCall
    public byte @NotNull [] toPrimitive(@NotNull Home complex, @NotNull PersistentDataAdapterContext context) {
        return new byte[0];
    }

    @SuppressWarnings("NullableProblems")
    public @Nullable Home fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(primitive);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

        try {
            while (dataInputStream.available() > 0) {
                String elem = dataInputStream.readUTF();

                if (elem.startsWith(target)) {
                    // Format is supposed to be "homeName worldName x y z"
                    String[] d = elem.split(" ");

                    double x = Double.parseDouble(d[2]);
                    double y = Double.parseDouble(d[3]);
                    double z = Double.parseDouble(d[4]);

                    Location location = new Location(Bukkit.getWorld(d[1]), x, y, z);

                    return new Home(d[0], location);
                }
            }
        } catch (IOException error) {
            error.printStackTrace();
        }

        return null;
    }
}
