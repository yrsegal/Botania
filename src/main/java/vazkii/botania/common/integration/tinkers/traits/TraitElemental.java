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
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import vazkii.botania.common.integration.tinkers.TinkersMaterials;

import java.util.HashSet;
import java.util.Set;

public class TraitElemental extends AbstractTrait {

    public TraitElemental() {
        super("elemental", TinkersMaterials.LIVINGROCK_COLOR);
    }

    private Set<EnumEnvironmentType> getEnvironment(EntityLivingBase player) {
        Set<EnumEnvironmentType> out = new HashSet<>();
        Biome biomeAt = player.worldObj.getBiomeForCoordsBody(player.getPosition());
        switch (biomeAt.getTempCategory()) {
            case COLD:
            case OCEAN:
                out.add(EnumEnvironmentType.WATER);
                break;
            case WARM:
                out.add(EnumEnvironmentType.FIRE);
                break;
        }

        if (player.posY < 32)
            out.add(EnumEnvironmentType.EARTH);
        if (player.posY > 128)
            out.add(EnumEnvironmentType.AIR);

        return out;
    }

    @Override
    public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical) {
        return getEnvironment(player).contains(EnumEnvironmentType.AIR) ? newKnockback * 1.5f : newKnockback;
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if (getEnvironment(event.getEntityPlayer()).contains(EnumEnvironmentType.EARTH))
            event.setNewSpeed(event.getNewSpeed() * 2f);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        return getEnvironment(player).contains(EnumEnvironmentType.FIRE) ? newDamage * 1.5f : newDamage;
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        if (getEnvironment(player).contains(EnumEnvironmentType.WATER)) {
            target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40, 1));
        }
    }

    private enum EnumEnvironmentType {
        FIRE, AIR,
        WATER, EARTH
    }
}
