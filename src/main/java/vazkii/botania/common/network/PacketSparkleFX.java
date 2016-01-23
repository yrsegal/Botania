package vazkii.botania.common.network;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import vazkii.botania.common.Botania;

import java.util.BitSet;

public class PacketSparkleFX implements IMessage {

    // Left to right: color, size
    private BitSet attributes = new BitSet(4);
    private double x, y, z;
    private float r, g, b;
    private float size;
    private int m;

    public PacketSparkleFX() {}

    public PacketSparkleFX(double x, double y, double z, float r, float g, float b, int m) {
        this(x, y, z, r, g, b, -1, m);
        attributes.set(0);
        attributes.clear(1);
    }

    public PacketSparkleFX(double x, double y, double z, float size, int m) {
        this(x, y, z, -1, -1, -1, size, m);
        attributes.clear(0);
        attributes.set(1);
    }

    public PacketSparkleFX(double x, double y, double z, int m) {
        this(x, y, z, -1, -1, -1, -1, m);
        attributes.clear(0);
        attributes.clear(1);
    }

    public PacketSparkleFX(double x, double y, double z, float r, float g, float b, float size, int m) {
        this.x = x; this.y = y; this.z = z;
        this.r = r; this.g = g; this.b = b;
        this.size = size;
        this.m = m;
        attributes.set(0);
        attributes.set(1);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        byte flags = buf.readByte();
        attributes = BitSet.valueOf(new byte[] {flags});
        x = buf.readInt() / 32.0D;
        y = buf.readInt() / 32.0D;
        z = buf.readInt() / 32.0D;

        if (attributes.get(0)) {
            r = buf.readInt() / 255F;
            g = buf.readInt() / 255F;
            b = buf.readInt() / 255F;
        } else {
            r = ((float) Math.random());
            g = ((float) Math.random());
            b = ((float) Math.random());
        }

        if (attributes.get(1)) {
            size = buf.readFloat();
        } else {
            size = ((float) Math.random());
        }

        m = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        byte[] flags = attributes.toByteArray();
        Preconditions.checkState(flags.length == 0 || flags.length == 1);
        buf.writeByte(flags.length == 0 ? 0 : flags[0]);

        buf.writeInt(MathHelper.floor_double(x * 32.0D));
        buf.writeInt(MathHelper.floor_double(y * 32.0D));
        buf.writeInt(MathHelper.floor_double(z * 32.0D));

        if (attributes.get(0)) {
            buf.writeInt(((int) (r * 255)));
            buf.writeInt(((int) (g * 255)));
            buf.writeInt(((int) (b * 255)));
        }

        if (attributes.get(1)) {
            buf.writeFloat(size);
        }

        buf.writeInt(m);
    }

    public static class Handler implements IMessageHandler<PacketSparkleFX, IMessage> {

        @Override
        public IMessage onMessage(final PacketSparkleFX msg, MessageContext ctx) {
            Runnable handler = () -> Botania.proxy.sparkleFX(Minecraft.getMinecraft().theWorld, msg.x, msg.y, msg.z, msg.r, msg.g, msg.b, msg.size, msg.m);

            IThreadListener t = Minecraft.getMinecraft();
            if (t.isCallingFromMinecraftThread()) {
                handler.run();
            } else {
                t.addScheduledTask(handler);
            }

            return null;
        }

    }

}
