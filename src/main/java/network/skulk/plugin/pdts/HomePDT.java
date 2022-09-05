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

    public HomePDT(final String target) {
        this.target = target.toLowerCase();
    }

    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    public @NotNull Class<Home> getComplexType() {
        return Home.class;
    }

    @DoNotCall
    public byte @NotNull [] toPrimitive(@NotNull final Home complex, @NotNull final PersistentDataAdapterContext context) {
        return new byte[0];
    }

    @SuppressWarnings("NullableProblems")
    public @Nullable Home fromPrimitive(final byte @NotNull [] primitive, @NotNull final PersistentDataAdapterContext context) {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(primitive);
        final DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

        try {
            while (dataInputStream.available() > 0) {
                final String elem = dataInputStream.readUTF();

                if (elem.startsWith(target)) {
                    // Format is supposed to be "homeName worldName x y z"
                    final String[] d = elem.split(" ");

                    final double x = Double.parseDouble(d[2]);
                    final double y = Double.parseDouble(d[3]);
                    final double z = Double.parseDouble(d[4]);
                    final float yaw = Float.parseFloat(d[5]);
                    final float pitch = Float.parseFloat(d[6]);

                    final Location location = new Location(Bukkit.getWorld(d[1]), x, y, z, yaw, pitch);

                    return new Home(d[0], location);
                }
            }
        } catch (final IOException error) {
            error.printStackTrace();
        }

        return null;
    }
}
