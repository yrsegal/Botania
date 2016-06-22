/**
 * This class was created by <WireSegal>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * <p>
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * <p>
 * File Created @ [Jun 22, 2016, 14:22:34 AM (GMT)]
 */
package vazkii.botania.common.integration.tinkers.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.traits.TraitProgressiveStats;
import vazkii.botania.common.integration.tinkers.TinkersMaterials;

public class TraitAlfwrought extends AbstractTrait {

    public TraitAlfwrought() {
        super("alfwrought", TinkersMaterials.ELEMENTIUM_COLOR);
    }

    private static int getIntTag(ItemStack stack, String key) {
        NBTTagCompound tag = TagUtil.getToolTag(stack);

        return tag.getInteger(key);
    }

    private static int getOrigIntTag(ItemStack stack, String key) {
        NBTTagCompound tag = TagUtil.getToolTag(stack);
        NBTTagCompound origTag = tag.getCompoundTag(Tags.TOOL_DATA_ORIG);
        return origTag.getInteger(key);
    }

    private static void setIntTag(ItemStack stack, String key, int value) {
        NBTTagCompound tag = TagUtil.getToolTag(stack);

        tag.setInteger(key, value);
    }

    @Override
    public void onRepair(ItemStack tool, int amount) {
        int newDurability = getIntTag(tool, Tags.DURABILITY) + Math.max(amount, 25);
        if (newDurability / getOrigIntTag(tool, Tags.DURABILITY) > 3) return;
        if (ToolHelper.getCurrentDurability(tool) == 0) {
            setIntTag(tool, Tags.DURABILITY, getIntTag(tool, Tags.DURABILITY) + Math.max(amount, 25));
        }
    }
}

