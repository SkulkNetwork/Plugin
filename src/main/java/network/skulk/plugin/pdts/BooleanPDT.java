package network.skulk.plugin.pdts;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class BooleanPDT implements PersistentDataType<Byte, Boolean> {
    private static final byte TRUE = 1, FALSE = 0;

    public @NotNull Class<Byte> getPrimitiveType() {
        return Byte.class;
    }

    public @NotNull Class<Boolean> getComplexType() {
        return Boolean.class;
    }

    public @NotNull Byte toPrimitive(@NotNull final Boolean complex, @NotNull final PersistentDataAdapterContext context) {
        return complex ? TRUE : FALSE;
    }

    public @NotNull Boolean fromPrimitive(@NotNull final Byte primitive, @NotNull final PersistentDataAdapterContext context) {
        return primitive == TRUE;
    }
}
