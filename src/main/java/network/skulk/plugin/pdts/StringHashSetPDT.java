package network.skulk.plugin.pdts;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashSet;


public final class StringHashSetPDT implements PersistentDataType<byte[], HashSet<String>> {
    @SuppressWarnings({"unchecked", "InstantiatingObjectToGetClassObject"})
    private static final Class<HashSet<String>> COMPLEX_TYPE = (Class<HashSet<String>>) new HashSet<String>(0).getClass();

    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    public @NotNull Class<HashSet<String>> getComplexType() {
        return COMPLEX_TYPE;
    }

    public byte @NotNull [] toPrimitive(@NotNull final HashSet<String> complex, @NotNull final PersistentDataAdapterContext context) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
            for (final String string : complex) {
                dataOutputStream.writeUTF(string);
            }
        } catch (final IOException error) {
            error.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    public @NotNull HashSet<String> fromPrimitive(final byte @NotNull [] primitive, @NotNull final PersistentDataAdapterContext context) {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(primitive);
        final DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

        final HashSet<String> hashSet = new HashSet<>();

        try {
            while (dataInputStream.available() > 0) {
                hashSet.add(dataInputStream.readUTF());
            }
        } catch (final IOException error) {
            error.printStackTrace();
        }

        return hashSet;
    }
}
