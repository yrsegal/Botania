/**
 * This class was created by <WireSegal>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * <p>
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * <p>
 * File Created @ [Jun 22, 2016, 12:58:34 AM (GMT)]
 */
package vazkii.botania.common.integration.tinkers;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import slimeknights.tconstruct.smeltery.block.BlockMolten;
import vazkii.botania.client.lib.LibResources;

import java.util.Locale;

public class FluidsRegistrationHelper {
    public static BlockMolten registerMoltenBlock(Fluid fluid) {
        BlockMolten block = new BlockMolten(fluid);
        return registerBlock(block, "molten_" + fluid.getName()); // molten_foobar prefix
    }

    public static <T extends Block> T registerBlock(T block, String name) {
        if (!name.equals(name.toLowerCase(Locale.US))) {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
        }

        String prefixedName = LibResources.PREFIX_MOD + name;
        block.setUnlocalizedName(prefixedName);

        register(block, name);
        return block;
    }

    public static <T extends IForgeRegistryEntry<?>> T register(T thing, String name) {
        thing.setRegistryName(new ResourceLocation(LibResources.PREFIX_MOD + name));
        GameRegistry.register(thing);
        return thing;
    }
}
