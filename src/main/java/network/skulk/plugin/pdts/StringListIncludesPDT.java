package network.skulk.plugin.pdts;

import com.google.errorprone.annotations.DoNotCall;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public final class StringListIncludesPDT implements PersistentDataType<byte[], Integer> {
    private final String target;

    public StringListIncludesPDT(final String target) {
        this.target = target;
    }

    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    public @NotNull Class<Integer> getComplexType() {
        return Integer.class;
    }

    @DoNotCall
    public byte @NotNull [] toPrimitive(@NotNull final Integer complex, @NotNull final PersistentDataAdapterContext context) {
        return new byte[0];
    }

    public @NotNull Integer fromPrimitive(final byte @NotNull [] primitive, @NotNull final PersistentDataAdapterContext context) {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(primitive);
        final DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

        try {
            while (dataInputStream.available() > 0) {
                final String elem = dataInputStream.readUTF();
                if (elem.equalsIgnoreCase(target)) {
                    return 1;
                }
                if (elem.equals(TPAExtension.IGNORE_ALL_SYMBOL)) {
                    return 2;
                }
            }
        } catch (final IOException error) {
            error.printStackTrace();
        }

        return 0;
    }
}
