package vazkii.botania.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import vazkii.botania.common.lib.LibMisc;

public final class PacketHandler {

    private static SimpleNetworkWrapper NET;

    public static void init() {
        NET = new SimpleNetworkWrapper(LibMisc.MOD_ID);
        NET.registerMessage(PacketSparkleFX.Handler.class, PacketSparkleFX.class, 0, Side.CLIENT);
        NET.registerMessage(PacketWispFX.Handler.class, PacketWispFX.class, 1, Side.CLIENT);
    }

    public static void sendToAllNear(IMessage msg, TileEntity te, int radius) {
        sendToAllNear(msg, te.getWorld().provider.getDimensionId(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), radius);
    }

    public static void sendToAllNear(IMessage msg, Entity e, int radius) {
        sendToAllNear(msg, e.worldObj.provider.getDimensionId(), e.posX, e.posY, e.posZ, radius);
    }

    public static void sendToAllNear(IMessage msg, int dim, double x, double y, double z, int radius) {
        NET.sendToAllAround(msg, new NetworkRegistry.TargetPoint(dim, x, y, z, radius));
    }

}
