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

    public StringListIncludesPDT(String target) {
        this.target = target;
    }

    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    public @NotNull Class<Integer> getComplexType() {
        return Integer.class;
    }

    @DoNotCall
    public byte @NotNull [] toPrimitive(@NotNull Integer complex, @NotNull PersistentDataAdapterContext context) {
        return new byte[0];
    }

    public @NotNull Integer fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(primitive);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

        try {
            while (dataInputStream.available() > 0) {
                String elem = dataInputStream.readUTF();
                if (elem.equalsIgnoreCase(target)) {
                    return 1;
                }
                if (elem.equals(TPAExtension.IGNORE_ALL_SYMBOL)) {
                    return 2;
                }
            }
        } catch (IOException error) {
            error.printStackTrace();
        }

        return 0;
    }
}
