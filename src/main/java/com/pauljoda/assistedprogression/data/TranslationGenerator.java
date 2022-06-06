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
    }
}
