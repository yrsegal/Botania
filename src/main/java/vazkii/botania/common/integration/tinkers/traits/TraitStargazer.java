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
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.Sys;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import vazkii.botania.common.integration.tinkers.TinkersMaterials;

public class TraitStargazer extends AbstractTrait {

    public TraitStargazer() {
        super("stargazer", TinkersMaterials.DREAMWOOD_COLOR);
    }

    private boolean isStargazing(EntityLivingBase entity) {
        float angle = MathHelper.cos(entity.worldObj.getCelestialAngleRadians(0));
        boolean nightTime = angle < 0;
        boolean canSeeSky = entity.worldObj.canBlockSeeSky(entity.getPosition());
        boolean ret = nightTime && canSeeSky;
        return ret;
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
        return isStargazing(entity) ? 0 : newDamage;
    }


}
