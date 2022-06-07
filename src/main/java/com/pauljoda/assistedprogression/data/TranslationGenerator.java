package com.pauljoda.assistedprogression.data;

import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 6/4/2022
 */
public class TranslationGenerator extends LanguageProvider {

    public TranslationGenerator(DataGenerator gen, String locale) {
        super(gen, Reference.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        // Creative Tab
        add("itemGroup." + Reference.MOD_ID, "Assisted Progression");
        add("itemGroup." + Reference.MOD_ID + "_pipettes", "Assisted Progression : Pipettes");

        // Items -------------------------------------------------------------------------------------------------------
        // Spawner Relocator
        add(Registration.SPAWNER_RELOCATOR_ITEM.get(), "Spawner Relocator");
        add("assisted_progression.text.spawnerRelocator.type", "%s%sType: %s");
        add("spawner_relocator.desc", "Hold right click and release while looking at a spawner to pick it up, hold right click while aiming at a block to place it back down");

        // Magnets
        add(Registration.MAGNET_ITEM.get(), "Magnet");
        add("item_cheap_magnet.desc", "Attracts all sorts of things, some dangerous. Shift+Right-Click to toggle activation");
        add(Registration.ELECTRIC_MAGNET_ITEM.get(), "Electric Magnet");
        add("item_electro_magnet.desc", "Attracts item entities and xp orbs, requires power. Shift+Right-Click to toggle activation");

        // Pipette
        add(Registration.PIPETTE_ITEM.get(), "Pipette");
        add("pipette.desc", "Holds up to one bucket of fluid, useful for removing fluid amounts less than one bucket. Right click fluid handling tiles to insert and extract fluid");
        add("assisted_progression.text.pipette.fluidStored", "Fluid Contained:");
        add("assisted_progression.text.pipette.empty", "Empty");

        // Parashoes
        add(Registration.PARASHOES_ITEM.get(), "Parashoes");
        add("parashoes.desc", "Wear these shoes to slow your decent");

        // Climbing Gloves
        add(Registration.CLIMBING_GLOVES_ITEM.get(), "Climbing Gloves");
        add("climbing_gloves.desc", "Have in your inventory to scale walls by walking into them");

        // Blocks ------------------------------------------------------------------------------------------------------
        // Ender Pad
        add(Registration.ENDER_PAD_BLOCK.get(), "Ender Pad");
        add("ender_pad.desc", "Place another in the path of the arrow on top and sneak while standing on one to teleport to the other");

        // Player Plate
        add(Registration.PLAYER_PLATE_BLOCK.get(), "Player Sensitive Pressure Plate");
        add("player_player.desc", "Pressure plate that will only activate for player entities");
    }
}
