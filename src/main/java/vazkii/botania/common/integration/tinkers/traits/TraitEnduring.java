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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
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

public class TraitEnduring extends AbstractTrait {

    private static final float MAX_CHANCE = 0.25f;

    public TraitEnduring() {
        super("enduring", TinkersMaterials.LIVINGROCK_COLOR);
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        float chance = (float) tool.getItemDamage() * MAX_CHANCE / (float) ToolHelper.getMaxDurability(tool);
        return Math.random() <= chance ? 0 : newDamage;
    }
}
