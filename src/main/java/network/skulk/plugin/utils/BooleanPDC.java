package network.skulk.plugin.utils;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

// PDC = BooleanPersistentDataType
// TODO: Remove suppressor.
@SuppressWarnings("unused")
public final class BooleanPDC implements PersistentDataType<Byte, Boolean> {
    private static final byte trueByte = 1, falseByte = 0;

    public @NotNull Class<Byte> getPrimitiveType() {
        return Byte.class;
    }

    public @NotNull Class<Boolean> getComplexType() {
        return Boolean.class;
    }

    public @NotNull Byte toPrimitive(@NotNull Boolean complex, @NotNull PersistentDataAdapterContext context) {
        return complex ? trueByte : falseByte;
    }

    public @NotNull Boolean fromPrimitive(@NotNull Byte primitive, @NotNull PersistentDataAdapterContext context) {
        return primitive == trueByte;
    }
}
