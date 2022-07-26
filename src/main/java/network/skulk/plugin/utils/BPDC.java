package network.skulk.plugin.utils;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

// BPDC = BooleanPermanentDataType
public final class BPDC implements PersistentDataType<Byte, Boolean> {
    private static final byte TrueByte = 1, FalseByte = 0;

    public @NotNull Class<Byte> getPrimitiveType() {
        return Byte.class;
    }

    public @NotNull Class<Boolean> getComplexType() {
        return Boolean.class;
    }

    public @NotNull Byte toPrimitive(@NotNull Boolean complex, @NotNull PersistentDataAdapterContext context) {
        return complex ? TrueByte : FalseByte;
    }

    public @NotNull Boolean fromPrimitive(@NotNull Byte primitive, @NotNull PersistentDataAdapterContext context) {
        return primitive == TrueByte;
    }
}
