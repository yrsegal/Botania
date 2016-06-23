/**
 * This class was created by <WireSegal>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * <p>
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * <p>
 * File Created @ [Jun 22, 2016, 1:30:05 AM (GMT)]
 */
package vazkii.botania.common.integration.tinkers;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import vazkii.botania.common.lib.LibTinkersNames;

public class TinkersFluids {

    public static Fluid manasteel;
    public static Fluid terrasteel;
    public static Fluid elementium;

    public static void init() {
        manasteel = new FluidMolten(LibTinkersNames.MANASTEEL, Util.enumChatFormattingToColor(TextFormatting.AQUA));
        manasteel.setTemperature(769);
        terrasteel = new FluidMolten(LibTinkersNames.TERRASTEEL, Util.enumChatFormattingToColor(TextFormatting.GREEN));
        terrasteel.setTemperature(1000);
        elementium = new FluidMolten(LibTinkersNames.ELEMENTIUM, Util.enumChatFormattingToColor(TextFormatting.LIGHT_PURPLE));
        elementium.setTemperature(769);

        registerFluid(manasteel);
        registerFluid(terrasteel);
        registerFluid(elementium);
    }

    private static void registerFluid(Fluid fluid) {
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);

        FluidsRegistrationHelper.registerMoltenBlock(fluid);
    }
}
