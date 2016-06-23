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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.handler.PixieHandler;
import vazkii.botania.common.integration.tinkers.TinkersMaterials;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.armor.elementium.ItemElementiumHelm;

public class TraitElsetouched extends AbstractTrait {

    public TraitElsetouched() {
        super("elsetouched", TinkersMaterials.ELEMENTIUM_COLOR);
    }

    public static boolean hasTrait(ItemStack stack) {
        NBTTagList list = TagUtil.getTraitsTagList(stack);
        for (int i = 0; i < list.tagCount(); i++) {
            ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
            if (trait instanceof TraitElsetouched) return true;
        }
        return false;
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        if (Math.random() < 0.25 && (!(player instanceof EntityPlayer) || ManaItemHandler.requestManaExact(tool, (EntityPlayer) player, 10, true))) {
            boolean useEffects = player instanceof EntityPlayer && ((ItemElementiumHelm) ModItems.elementiumHelm).hasArmorSet((EntityPlayer) player);
            PixieHandler.summonPixie(player, target, damage / 2f, useEffects);
        }
    }

    @Override
    public void beforeBlockBreak(ItemStack tool, BlockEvent.BreakEvent event) {
        ModItems.elementiumShovel.onBlockStartBreak(tool, event.getPos(), event.getPlayer());
    }
}

