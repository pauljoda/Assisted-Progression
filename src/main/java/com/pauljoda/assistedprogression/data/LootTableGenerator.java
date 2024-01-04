package com.pauljoda.assistedprogression.data;

import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.data.BaseLootTableGenerator;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

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

    @Override
    protected void generate() {

        // Ender Pad
        createStandardTable(Registration.ENDER_PAD_BLOCK.get(), Registration.ENDER_PAD_BLOCK_ENTITY.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream()
                .map(DeferredHolder::get)
                .collect(Collectors.toList());
    }
}