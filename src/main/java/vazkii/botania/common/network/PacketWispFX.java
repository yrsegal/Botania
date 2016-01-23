package vazkii.botania.common.network;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import vazkii.botania.common.Botania;

import java.util.BitSet;

public class PacketWispFX implements IMessage {

    // Specifies what will be sent over the network. Anything that is not will be regenerated clientside (randomly).
    // Left to right: color, size, velocity, maxAgeMul
    private BitSet attributes = new BitSet(4);
    private double x, y, z;
    private float r, g, b;
    private float size;
    private float mX, mY, mZ;
    private float maxAgeMul;

    public PacketWispFX() {}

    public PacketWispFX(double x, double y, double z, float r, float g, float b, float size) {
        this.x = x; this.y = y; this.z = z;
        this.r = r; this.g = g; this.b = b;
        this.size = size;
        attributes.set(0);
        attributes.set(1);
        attributes.clear(2);
        attributes.clear(3);
    }

    public PacketWispFX(double x, double y, double z, float r, float g, float b, float size, float mX, float mY, float mZ) {
        this.x = x; this.y = y; this.z = z;
        this.r = r; this.g = g; this.b = b;
        this.size = size;
        this.mX = mX; this.mY = mY; this.mZ = mZ;
        attributes.set(0);
        attributes.set(1);
        attributes.set(2);
        attributes.clear(3);
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

        if (attributes.get(2)) {
            mX = buf.readShort() / 8000.0F;
            mY = buf.readShort() / 8000.0F;
            mZ = buf.readShort() / 8000.0F;
        } else {
            mX = 0F;
            mY = 0F;
            mZ = 0F;
        }

        if (attributes.get(3)) {
            maxAgeMul = buf.readFloat();
        } else {
            maxAgeMul = 1F;
        }
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
            buf.writeInt(MathHelper.floor_float(r * 255F));
            buf.writeInt(MathHelper.floor_float(g * 255F));
            buf.writeInt(MathHelper.floor_float(b * 255F));
        }

        if (attributes.get(1)) {
            buf.writeFloat(size);
        }

        if (attributes.get(2)) {
            buf.writeShort(((int) (mX * 8000.0D)));
            buf.writeShort(((int) (mY * 8000.0D)));
            buf.writeShort(((int) (mZ * 8000.0D)));
        }

        if (attributes.get(3)) {
            buf.writeFloat(maxAgeMul);
        }
    }

    public static class Handler implements IMessageHandler<PacketWispFX, IMessage> {

        @Override
        public IMessage onMessage(PacketWispFX msg, MessageContext ctx) {
            Runnable handler = () -> Botania.proxy.wispFX(Minecraft.getMinecraft().theWorld, msg.x, msg.y, msg.z, msg.r, msg.g, msg.b, msg.size, msg.mX, msg.mY, msg.mZ, msg.maxAgeMul);

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
