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
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import vazkii.botania.common.integration.tinkers.TinkersMaterials;

public class TraitStargazer extends AbstractTrait {

    public TraitStargazer() {
        super("stargazer", TinkersMaterials.DREAMWOOD_COLOR);
    }

    private boolean isStargazing(EntityLivingBase entity) {
        float angle = MathHelper.cos(entity.worldObj.getCelestialAngleRadians(0));
        boolean nightTime = angle < 0;
        boolean canSeeSky = entity.worldObj.canBlockSeeSky(entity.getPosition());
        return nightTime && canSeeSky;
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if (isStargazing(event.getEntityPlayer()))
            event.setNewSpeed(event.getNewSpeed() * 1.5f);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        return isStargazing(player) ? newDamage * 1.5f : newDamage;
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        return isStargazing(entity) && Math.random() < 0.5f ? 0 : newDamage;
    }


}
