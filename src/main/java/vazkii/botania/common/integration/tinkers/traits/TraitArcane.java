/**
 * This class was created by <WireSegal>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * <p>
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * <p>
 * File Created @ [Jun 22, 2016, 10:13:34 AM (GMT)]
 */
package vazkii.botania.common.integration.tinkers.traits;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import vazkii.botania.common.integration.tinkers.TinkersMaterials;

public class TraitArcane extends AbstractTrait {

    private static final int CONVERSION_RATE = 1;

    public TraitArcane() {
        super("arcane", TinkersMaterials.MANASTEEL_COLOR);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onGetXP(PlayerPickupXpEvent e) {
        EntityPlayer player = e.getEntityPlayer();
        ItemStack held = player.getHeldItemMainhand();
        ItemStack heldOff = player.getHeldItemOffhand();
        if (held != null && held.getItem() instanceof ToolCore && hasTrait(held) && !ToolHelper.isBroken(held)) {
            int i = Math.min(e.getOrb().xpValue / CONVERSION_RATE, held.getItemDamage());
            e.getOrb().xpValue -= i * CONVERSION_RATE;
            ToolHelper.repairTool(held, i, player);
            if (i != 0) return;
        }

        if (heldOff != null && heldOff.getItem() instanceof ToolCore && hasTrait(heldOff) && !ToolHelper.isBroken(heldOff)) {
            int i = Math.min(e.getOrb().xpValue / CONVERSION_RATE, heldOff.getItemDamage());
            e.getOrb().xpValue -= i * CONVERSION_RATE;
            ToolHelper.repairTool(heldOff, i, player);
        }
    }

    private boolean hasTrait(ItemStack stack) {
        NBTTagList list = TagUtil.getTraitsTagList(stack);
        for (int i = 0; i < list.tagCount(); i++) {
            ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
            if (trait == this) return true;
        }
        return false;
    }
}
