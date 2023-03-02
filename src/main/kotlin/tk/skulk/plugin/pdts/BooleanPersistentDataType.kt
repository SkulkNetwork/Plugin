package tk.skulk.plugin.pdts

import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

class BooleanPersistentDataType : PersistentDataType<Byte, Boolean> {
    override fun getPrimitiveType() = Byte::class.java

    override fun getComplexType() = Boolean::class.java


    override fun toPrimitive(
        complex: Boolean, context: PersistentDataAdapterContext,
    ) = (if (complex) 1 else 0).toByte()

    override fun fromPrimitive(
        primitive: Byte, context: PersistentDataAdapterContext,
    ) = primitive == 1.toByte()
}
