/**
 * This class was created by <WireSegal>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * <p>
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * <p>
 * File Created @ [Jun 22, 2016, 20:41:34 AM (GMT)]
 */
package vazkii.botania.common.integration.tinkers.traits;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.library.tools.IAoeTool;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.api.sound.BotaniaSoundEvents;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.integration.tinkers.TinkersMaterials;
import vazkii.botania.common.item.equipment.tool.elementium.ItemElementiumPick;

public class TraitWorldforged extends AbstractTrait {
    private static BlockPos lastPos;
    private static double lastDistance;

    public TraitWorldforged() {
        super("worldforged", TinkersMaterials.TERRASTEEL_COLOR);
    }

    public static boolean breakFurthestBlock(ItemStack tool, World world, BlockPos pos, Block block, EntityPlayer player) {
        if (ItemElementiumPick.isDisposable(block) || ItemElementiumPick.isSemiDisposable(block) || block == Blocks.STONE)
            return false;

        IBlockState state = world.getBlockState(pos);

        boolean flag = false;
        for (String toolType : tool.getItem().getToolClasses(tool))
            if (block.isToolEffective(toolType, state)) flag = true;
        if (!flag) return false;

        lastPos = pos;
        lastDistance = 0.0;
        findBlocks(world, pos, state);
        return harvestBlock(tool, world, player, lastPos);
    }

    public static void findBlocks(World world, BlockPos pos, IBlockState originalState) {
        for (int xx = -2; xx <= 2; xx++) {
            for (int yy = 2; yy >= -2; yy--) {
                for (int zz = -2; zz <= 2; zz++) {
                    if (Math.abs((lastPos.getX() + xx) - pos.getX()) > 24)
                        return;
                    if (Math.abs((lastPos.getY() + yy) - pos.getY()) > 48)
                        return;
                    if (Math.abs((lastPos.getZ() + zz) - pos.getZ()) > 24)
                        return;
                    IBlockState state = world.getBlockState(lastPos.add(xx, yy, zz));
                    if (originalState.getBlock() != state.getBlock() || state.withRotation(Rotation.NONE) != state.withRotation(Rotation.NONE))
                        continue;
                    double xd = (lastPos.getX() + xx) - pos.getX();
                    double yd = (lastPos.getY() + yy) - pos.getY();
                    double zd = (lastPos.getZ() + zz) - pos.getZ();
                    double d = xd * xd + yd * yd + zd * zd;
                    if (d > lastDistance) {
                        lastDistance = d;
                        lastPos = lastPos.add(xx, yy, zz);
                        findBlocks(world, pos, originalState);
                        return;
                    }
                }
            }
        }
    }

    public static boolean harvestBlock(ItemStack tool, World world, EntityPlayer player, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        if (!ForgeHooks.canHarvestBlock(state.getBlock(), player, world, pos)) return false;
        removeBlockWithDrops(player, tool, world, pos);
        return true;
    }

    public static void removeBlockWithDrops(EntityPlayer player, ItemStack stack, World world, BlockPos pos) {
        if (!world.isBlockLoaded(pos))
            return;

        IBlockState state = world.getBlockState(pos);
        Block blk = state.getBlock();

        if (!world.isRemote && !blk.isAir(state, world, pos) && state.getPlayerRelativeBlockHardness(player, world, pos) > 0) {
            if (!player.capabilities.isCreativeMode) {
                IBlockState localState = world.getBlockState(pos);
                blk.onBlockHarvested(world, pos, localState, player);

                if (blk.removedByPlayer(state, world, pos, player, true)) {
                    blk.onBlockDestroyedByPlayer(world, pos, state);
                    blk.harvestBlock(world, player, pos, state, world.getTileEntity(pos), stack);
                }

                ToolHelper.damageTool(stack, 1, player);

            } else world.setBlockToAir(pos);

            if (ConfigHandler.blockBreakParticles && ConfigHandler.blockBreakParticlesTool)
                world.playEvent(2001, pos, Block.getStateId(state));
        }
    }

    @Override
    public void beforeBlockBreak(ItemStack tool, BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        World world = player.worldObj;
        if (!player.isSneaking()) {
            if (!world.isRemote) {
                if (breakFurthestBlock(tool, world, event.getPos(), event.getState().getBlock(), player)) {
                    if (!(tool.getItem() instanceof IAoeTool)) event.setCanceled(true);
                    world.playSound(null, event.getPos(), BotaniaSoundEvents.endoflame, SoundCategory.PLAYERS, 0.15F, 1.0F);
                }
            }
        }
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        if (Math.random() < 0.15f && (!(player instanceof EntityPlayer) || ManaItemHandler.requestManaExact(tool, (EntityPlayer) player, 10, true))) {
            target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 30, -5, true, false));
            target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30));
        }
    }
}
