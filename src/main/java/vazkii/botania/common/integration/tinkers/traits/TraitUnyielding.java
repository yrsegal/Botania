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
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import vazkii.botania.common.integration.tinkers.TinkersMaterials;

public class TraitUnyielding extends AbstractTrait {

    public TraitUnyielding() {
        super("unyielding", TinkersMaterials.TERRASTEEL_COLOR);
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        if (ToolHelper.getCurrentDurability(tool) >= 5f) return newDamage;
        float chance = 0.75f;
        return Math.random() <= chance ? 0 : newDamage;
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (ToolHelper.getCurrentDurability(tool) < 5f)
            newDamage /= 2.5;

        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if (ToolHelper.getCurrentDurability(tool) < 5f)
            event.setNewSpeed(event.getNewSpeed() / 2.5f);
    }
}

