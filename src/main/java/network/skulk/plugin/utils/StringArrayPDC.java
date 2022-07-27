package network.skulk.plugin.utils;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

final class StringArrayList extends ArrayList<String> {
}

public final class StringArrayPDC implements PersistentDataType<byte[], StringArrayList> {
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    public @NotNull Class<StringArrayList> getComplexType() {
        return StringArrayList.class;
    }

    public byte @NotNull [] toPrimitive(@NotNull StringArrayList complex, @NotNull PersistentDataAdapterContext context) {
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

    public @NotNull StringArrayList fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(primitive);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

        StringArrayList arrayList = new StringArrayList();

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
