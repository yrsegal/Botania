package vazkii.botania.common.core.handler;

import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.item.IPixieSpawner;
import vazkii.botania.common.core.helper.PlayerHelper;
import vazkii.botania.common.entity.EntityPixie;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.armor.elementium.ItemElementiumHelm;

public class PixieHandler {

	private static final Potion[] potions = {
			MobEffects.BLINDNESS,
			MobEffects.WITHER,
			MobEffects.SLOWNESS,
			MobEffects.WEAKNESS
	};

	@SubscribeEvent
	public void onDamageTaken(LivingHurtEvent event) {
		if(!event.getEntityLiving().worldObj.isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getSource().getEntity() != null && event.getSource().getEntity() instanceof EntityLivingBase) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			ItemStack stack = PlayerHelper.getFirstHeldItemClass(player, IPixieSpawner.class);

			float chance = getChance(stack);
			for (ItemStack element : player.inventory.armorInventory)
				chance += getChance(element);

			InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);
			for(int i = 0; i < baubles.getSizeInventory(); i++)
				chance += getChance(baubles.getStackInSlot(i));

			if(Math.random() < chance) {
				float dmg = 4;
				if(stack != null && stack.getItem() == ModItems.elementiumSword)
					dmg += 2;
				boolean useEffects = ((ItemElementiumHelm) ModItems.elementiumHelm).hasArmorSet(player);
				summonPixie(player, (EntityLivingBase) event.getSource().getEntity(), dmg, useEffects);
			}
		}
	}

	public static void summonPixie(EntityLivingBase summoner, EntityLivingBase target, float damage, boolean useEffects) {
		if (summoner.worldObj.isRemote) return;

		EntityPixie pixie = new EntityPixie(summoner.worldObj);
		pixie.setPosition(summoner.posX, summoner.posY + 2, summoner.posZ);

		if(useEffects)
			pixie.setApplyPotionEffect(new PotionEffect(potions[summoner.worldObj.rand.nextInt(potions.length)], 40, 0));

		pixie.setProps(target, summoner, 0, damage);
		summoner.worldObj.spawnEntityInWorld(pixie);
	}

	private float getChance(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof IPixieSpawner))
			return 0F;
		else return ((IPixieSpawner) stack.getItem()).getPixieChance(stack);
	}

}
