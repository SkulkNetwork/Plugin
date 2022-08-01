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

    public byte @NotNull [] toPrimitive(@NotNull HashSet<String> complex, @NotNull PersistentDataAdapterContext context) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
            for (String string : complex) {
                dataOutputStream.writeUTF(string);
            }
        } catch (IOException error) {
            error.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    public @NotNull HashSet<String> fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(primitive);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

        HashSet<String> hashSet = new HashSet<>();

        try {
            while (dataInputStream.available() > 0) {
                hashSet.add(dataInputStream.readUTF());
            }
        } catch (IOException error) {
            error.printStackTrace();
        }

        return hashSet;
    }
}
