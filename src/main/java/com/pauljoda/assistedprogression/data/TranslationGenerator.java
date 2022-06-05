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

        // Items -------------------------------------------------------------------------------------------------------
        // Spawner Relocator
        add(Registration.SPAWNER_RELOCATOR_ITEM.get(), "Spawner Relocator");
        add("assisted_progression.text.spawnerRelocator.type", "%s%sType: %s");
        add("spawner_relocator.desc", "Hold right click and release while looking at a spawner to pick it up, hold right click while aiming at a block to place it back down");

        // Magnets
        add(Registration.MAGNET_ITEM.get(), "Magnet");
        add("item_cheap_magnet.desc", "Attracts all sorts of things, some dangerous. Shift+Right-Click to toggle activation");
    }
}
