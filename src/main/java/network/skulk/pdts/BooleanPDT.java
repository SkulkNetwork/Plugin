package network.skulk.pdts;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public final class BooleanPDT implements PersistentDataType<Byte, Boolean> {
    private static final byte TRUE = 1, FALSE = 0;

    @Override
    public Class<Byte> getPrimitiveType() {
        return Byte.class;
    }

    @Override
    public Class<Boolean> getComplexType() {
        return Boolean.class;
    }

    @Override
    public Byte toPrimitive(final Boolean complex, final PersistentDataAdapterContext context) {
        return complex ? TRUE : FALSE;
    }

    @Override
    public Boolean fromPrimitive(final Byte primitive, final PersistentDataAdapterContext context) {
        return primitive == TRUE;
    }
}