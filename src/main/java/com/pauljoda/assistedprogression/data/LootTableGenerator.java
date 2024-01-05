package com.pauljoda.assistedprogression.data;

import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.data.BaseLootTableGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
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
        createSimpleTable(Registration.ENDER_PAD_BLOCK.get());

        // Sun
        createSimpleTable(Registration.SUN_BLOCK.get());

        // Player Plate
        createSimpleTable(Registration.PLAYER_PLATE_BLOCK.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream()
                .map(DeferredHolder::get)
                .collect(Collectors.toList());
    }
}