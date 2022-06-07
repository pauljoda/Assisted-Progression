package com.pauljoda.assistedprogression.data;

import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.data.BaseLootTableGenerator;
import net.minecraft.data.DataGenerator;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 6/7/2022
 */
public class LootTableGenerator extends BaseLootTableGenerator {

    public LootTableGenerator(DataGenerator dataGenerator) {
        super(dataGenerator, "Assisted Progression Loot");
    }

    @Override
    protected void addTables() {
        // Ender Pad
        lootTables.put(Registration.ENDER_PAD_BLOCK.get(),
                createSimpleTable("ender_pad", Registration.ENDER_PAD_BLOCK.get()));

        // Player Plate
        lootTables.put(Registration.PLAYER_PLATE_BLOCK.get(),
                createSimpleTable("player_plate", Registration.PLAYER_PLATE_BLOCK.get()));
    }
}
