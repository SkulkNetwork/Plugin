package network.skulk.plugin.pdts;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;


public final class StringArrayPDT implements PersistentDataType<byte[], ArrayList<String>> {
    @SuppressWarnings({"unchecked", "InstantiatingObjectToGetClassObject"})
    private static final Class<ArrayList<String>> COMPLEX_TYPE = (Class<ArrayList<String>>) new ArrayList<String>(0).getClass();

    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    public @NotNull Class<ArrayList<String>> getComplexType() {
        return COMPLEX_TYPE;
    }

    public byte @NotNull [] toPrimitive(@NotNull ArrayList<String> complex, @NotNull PersistentDataAdapterContext context) {
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

    public @NotNull ArrayList<String> fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(primitive);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

        ArrayList<String> arrayList = new ArrayList<>();

        try {
            while (dataInputStream.available() > 0) {
                arrayList.add(dataInputStream.readUTF());
            }
        } catch (IOException error) {
            error.printStackTrace();
        }

        return arrayList;
    }
}
