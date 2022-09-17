package network.skulk.pdts;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class BooleanPDT implements PersistentDataType<Byte, Boolean> {
    private static final byte TRUE = 1, FALSE = 0;

    @Override
    public @NotNull Class<Byte> getPrimitiveType() {
        return Byte.class;
    }

    @Override
    public @NotNull Class<Boolean> getComplexType() {
        return Boolean.class;
    }

    @Override
    public @NotNull Byte toPrimitive(final @NotNull Boolean complex, final @NotNull PersistentDataAdapterContext context) {
        return complex ? TRUE : FALSE;
    }

    @Override
    public @NotNull Boolean fromPrimitive(final @NotNull Byte primitive, final @NotNull PersistentDataAdapterContext context) {
        return primitive == TRUE;
    }
}