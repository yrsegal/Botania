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
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import vazkii.botania.common.integration.tinkers.TinkersMaterials;

public class TraitRegrowth extends AbstractTrait {

    public TraitRegrowth() {
        super("regrowth", TinkersMaterials.LIVINGWOOD_COLOR);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        int chance = 30;
        if (!world.isRemote && entity instanceof EntityLivingBase && random.nextInt(20 * chance) == 0) {
            ToolHelper.healTool(tool, 1, (EntityLivingBase) entity);
        }
    }
}

