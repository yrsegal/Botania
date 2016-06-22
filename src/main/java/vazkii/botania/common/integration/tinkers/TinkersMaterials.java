/**
 * This class was created by <WireSegal>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * <p>
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * <p>
 * File Created @ [Jun 22, 2016, 10:07:34 AM (GMT)]
 */
package vazkii.botania.common.integration.tinkers;

import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.TinkerIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.tools.TinkerMaterials;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.integration.tinkers.traits.*;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.LibOreDict;
import vazkii.botania.common.lib.LibTinkersNames;

import static slimeknights.tconstruct.library.utils.HarvestLevels.*;

public class TinkersMaterials {

    public static final int MANASTEEL_COLOR = 0x3E93E8;
    public static final int TERRASTEEL_COLOR = 0x109307;
    public static final int ELEMENTIUM_COLOR = 0xE83ED7;
    public static final int LIVINGWOOD_COLOR = 0x4C290A;
    public static final int DREAMWOOD_COLOR = 0xC8F4EE;
    public static final int LIVINGROCK_COLOR = 0xFFEBD1;

    public static final AbstractTrait arcane = new TraitArcane();

//    public static final AbstractTrait worldformed = new TraitWorldformed();
    public static final AbstractTrait unyielding = new TraitUnyielding();

//    public static final AbstractTrait elsetouched = new TraitElsetouched();
    public static final AbstractTrait alfwrought = new TraitAlfwrought();

    public static final AbstractTrait regrowth = new TraitRegrowth();

//    public static final AbstractTrait idealistic = new TraitIdealistic();
    public static final AbstractTrait stargazer = new TraitStargazer();

//    public static final AbstractTrait elemental = new TraitElemental();
    public static final AbstractTrait enduring = new TraitEnduring();

    public static Material manasteel = mat(LibTinkersNames.MANASTEEL, MANASTEEL_COLOR);
    public static Material terrasteel = mat(LibTinkersNames.TERRASTEEL, TERRASTEEL_COLOR);
    public static Material elementium = mat(LibTinkersNames.ELEMENTIUM, ELEMENTIUM_COLOR);

    public static Material livingwood = mat(LibTinkersNames.LIVINGWOOD, LIVINGWOOD_COLOR);
    public static Material dreamwood = mat(LibTinkersNames.DREAMWOOD, DREAMWOOD_COLOR);

    public static Material livingrock = mat(LibTinkersNames.LIVINGROCK, LIVINGROCK_COLOR);

    public static void init() {
        manasteel.addItem(LibOreDict.MANA_STEEL, 1, Material.VALUE_Ingot);
        manasteel.setRepresentativeItem(new ItemStack(ModItems.manaResource, 1, 0));
        manasteel.setRenderInfo(new MaterialRenderInfo.Metal(manasteel.materialTextColor, 0.25f, 0.5f, -0.1f));
        manasteel.addTrait(arcane);
        TinkerRegistry.addMaterialStats(manasteel,
                new HeadMaterialStats(204, 6.00f, 4.00f, OBSIDIAN),
                new HandleMaterialStats(0.85f, 60),
                new ExtraMaterialStats(50));

        terrasteel.addItem(LibOreDict.TERRA_STEEL, 1, Material.VALUE_Ingot);
        terrasteel.setRepresentativeItem(new ItemStack(ModItems.manaResource, 1, 4));
        terrasteel.setRenderInfo(new MaterialRenderInfo.Metal(terrasteel.materialTextColor, 0.25f, 0.5f, -0.1f));
//        terrasteel.addTrait(worldformed, HeadMaterialStats.TYPE);
        terrasteel.addTrait(unyielding);
        TinkerRegistry.addMaterialStats(terrasteel,
                new HeadMaterialStats(540, 7.00f, 6.00f, COBALT),
                new HandleMaterialStats(0.9f, 150),
                new ExtraMaterialStats(25));

        elementium.addItem(LibOreDict.ELEMENTIUM, 1, Material.VALUE_Ingot);
        elementium.setRepresentativeItem(new ItemStack(ModItems.manaResource, 1, 7));
        elementium.setRenderInfo(new MaterialRenderInfo.Metal(elementium.materialTextColor, 0.25f, 0.5f, -0.1f));
//        elementium.addTrait(elsetouched, HeadMaterialStats.TYPE);
        elementium.addTrait(alfwrought);
        TinkerRegistry.addMaterialStats(elementium,
                new HeadMaterialStats(204, 6.00f, 4.00f, OBSIDIAN),
                new HandleMaterialStats(0.85f, 60),
                new ExtraMaterialStats(50));

        livingwood.setCraftable(true);
        livingwood.addItem(new ItemStack(ModBlocks.livingwood, 1, 1), 1, Material.VALUE_Fragment);
        livingwood.addItem(LibOreDict.LIVING_WOOD, 1, Material.VALUE_Ingot);
        livingwood.addItem(LibOreDict.LIVINGWOOD_TWIG, 1, Material.VALUE_Ingot * 2);
        livingwood.setRepresentativeItem(new ItemStack(ModItems.manaResource, 1, 3));
        livingwood.setRenderInfo(livingwood.materialTextColor);
        livingwood.addTrait(regrowth);
        TinkerRegistry.addMaterialStats(livingwood,
                new HeadMaterialStats(15, 2.00f, 2.00f, STONE),
                new HandleMaterialStats(1.00f, 0),
                new ExtraMaterialStats(0));

        dreamwood.setCraftable(true);
        dreamwood.addItem(new ItemStack(ModBlocks.dreamwood, 1, 1), 1, Material.VALUE_Fragment);
        dreamwood.addItem(LibOreDict.DREAM_WOOD, 1, Material.VALUE_Ingot);
        dreamwood.addItem(LibOreDict.DREAMWOOD_TWIG, 1, Material.VALUE_Ingot * 2);
        dreamwood.setRepresentativeItem(new ItemStack(ModItems.manaResource, 1, 13));
        dreamwood.setRenderInfo(dreamwood.materialTextColor);
//        dreamwood.addTrait(idealistic, HeadMaterialStats.TYPE);
        dreamwood.addTrait(stargazer);
        TinkerRegistry.addMaterialStats(dreamwood,
                new HeadMaterialStats(15, 2.00f, 2.00f, STONE),
                new HandleMaterialStats(1.00f, 0),
                new ExtraMaterialStats(0));

        livingrock.setCraftable(true);
        livingrock.addItem(LibOreDict.LIVING_ROCK, 1, Material.VALUE_Ingot);
        livingrock.setRenderInfo(livingrock.materialTextColor);
//        livingrock.addTrait(elemental, HeadMaterialStats.TYPE);
        livingrock.addTrait(enduring);
        TinkerRegistry.addMaterialStats(livingrock,
                new HeadMaterialStats(60, 4.00f, 2.90f, IRON),
                new HandleMaterialStats(0.50f, 0),
                new ExtraMaterialStats(0));

        TinkerIntegration.integrate(manasteel, TinkersFluids.manasteel, LibOreDict.MANA_STEEL.replace("ingot", "")).toolforge().integrate();
        TinkerIntegration.integrate(terrasteel, TinkersFluids.terrasteel, LibOreDict.TERRA_STEEL.replace("ingot", "")).toolforge().integrate();
        TinkerIntegration.integrate(elementium, TinkersFluids.elementium, LibOreDict.ELEMENTIUM.replace("ingot", "")).toolforge().integrate();
        TinkerIntegration.integrate(livingwood).integrate();
        TinkerIntegration.integrate(dreamwood).integrate();
        TinkerIntegration.integrate(livingrock).integrate();
    }

    private static Material mat(String name, int color) {
        Material mat = new Material(name, color);
        TinkerMaterials.materials.add(mat);
        return mat;
    }

}
