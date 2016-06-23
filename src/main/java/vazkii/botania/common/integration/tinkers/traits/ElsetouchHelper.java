/**
 * This class was created by <WireSegal>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * <p>
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * <p>
 * File Created @ [Jun 22, 2016, 19:28:34 AM (GMT)]
 */
package vazkii.botania.common.integration.tinkers.traits;

import net.minecraft.item.ItemStack;
import vazkii.botania.common.Botania;

public final class ElsetouchHelper {
    public static boolean hasElsetouch(ItemStack stack) {
        return Botania.tinkersLoaded && TraitElsetouched.hasTrait(stack);
    }
}
