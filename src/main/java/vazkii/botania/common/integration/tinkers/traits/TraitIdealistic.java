/**
 * This class was created by <WireSegal>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * <p>
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * <p>
 * File Created @ [Jun 22, 2016, 15:51:34 AM (GMT)]
 */
package vazkii.botania.common.integration.tinkers.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import vazkii.botania.common.integration.tinkers.TinkersMaterials;

public class TraitIdealistic extends AbstractTrait {
    public TraitIdealistic() {
        super("idealistic", TinkersMaterials.DREAMWOOD_COLOR);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (target.getHealth() <= 10) return newDamage;

        return newDamage + (target.getHealth() - 10) / 5;
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        float hardness = event.getState().getBlockHardness(event.getEntityPlayer().worldObj, event.getPos());
        if (hardness == 0.0F)
            event.setNewSpeed(1.0F);
        else if (hardness < 5.0F)
            event.setNewSpeed(0.075F * event.getNewSpeed());
        else if (hardness < 20.0F)
            event.setNewSpeed(0.375F * event.getNewSpeed() + hardness);
        else
            event.setNewSpeed(3.375F * event.getNewSpeed() + hardness);
    }
}
