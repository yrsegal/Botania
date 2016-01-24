package vazkii.botania.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;

public class PacketLightningFX implements IMessage {

    private Vector3 start, end;
    private float ticksPerMeter;
    private int colorOuter, colorInner;
    private long seed;
    private boolean hasSeed;

    public PacketLightningFX() {}

    public PacketLightningFX(Vector3 vectorStart, Vector3 vectorEnd, float ticksPerMeter, int colorOuter, int colorInner) {
        this(vectorStart, vectorEnd, ticksPerMeter, -1, colorOuter, colorInner);
        this.hasSeed = false;
    }

    public PacketLightningFX(Vector3 vectorStart, Vector3 vectorEnd, float ticksPerMeter, long seed, int colorOuter, int colorInner) {
        this.start = vectorStart; this.end = vectorEnd;
        this.ticksPerMeter = ticksPerMeter;
        this.colorOuter = colorOuter; this.colorInner = colorInner;
        this.seed = seed;
        this.hasSeed = true;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        start = new Vector3();
        start.x = buf.readInt() / 32.0D;
        start.y = buf.readInt() / 32.0D;
        start.z = buf.readInt() / 32.0D;

        end = new Vector3();
        end.x = buf.readInt() / 32.0D;
        end.y = buf.readInt() / 32.0D;
        end.z = buf.readInt() / 32.0D;

        ticksPerMeter = buf.readFloat();

        colorOuter = buf.readInt();
        colorInner = buf.readInt();

        hasSeed = buf.readBoolean();

        if (hasSeed) {
            seed = buf.readLong();
        } else {
            seed = System.nanoTime();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(MathHelper.floor_double(start.x * 32.0D));
        buf.writeInt(MathHelper.floor_double(start.y * 32.0D));
        buf.writeInt(MathHelper.floor_double(start.z * 32.0D));

        buf.writeInt(MathHelper.floor_double(end.x * 32.0D));
        buf.writeInt(MathHelper.floor_double(end.y * 32.0D));
        buf.writeInt(MathHelper.floor_double(end.z * 32.0D));

        buf.writeFloat(ticksPerMeter);

        buf.writeInt(colorOuter);
        buf.writeInt(colorInner);

        buf.writeBoolean(hasSeed);

        if (hasSeed) {
            buf.writeLong(seed);
        }
    }

    public static class Handler implements IMessageHandler<PacketLightningFX, IMessage> {

        @Override
        public IMessage onMessage(final PacketLightningFX msg, MessageContext ctx) {
            Runnable handler = () -> Botania.proxy.lightningFX(Minecraft.getMinecraft().theWorld, msg.start, msg.end, msg.ticksPerMeter, msg.seed, msg.colorOuter, msg.colorInner);

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
