package com.pauljoda.assistedprogression.data;

import com.pauljoda.assistedprogression.lib.Reference;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;

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
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        // Generate Server Resources
        if (event.includeServer()) {
            // Item Tags
            //generator.addProvider(new ItemTagGenerator(generator, blockTagsProvider, event.getExistingFileHelper()));

        }
        // Server
        // Block Tags
        BlockTagsProvider blockTagsProvider = new BlockTagGenerator(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTagsProvider);
        // Loot Tables
        generator.addProvider(event.includeServer(), new LootTableProvider(generator.getPackOutput(), Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(LootTableGenerator::new, LootContextParamSets.BLOCK))));
        // Recipes
        generator.addProvider(event.includeServer(), new RecipeGenerator(generator.getPackOutput(), event.getLookupProvider()));

        // Client
        // Block States
        generator.addProvider(event.includeClient(), new BlockStateGenerator(generator.getPackOutput(), event.getExistingFileHelper()));
        // Item Models
        generator.addProvider(event.includeClient(), new ItemModelGenerator(generator.getPackOutput(), event.getExistingFileHelper()));
        // Translation
        generator.addProvider(event.includeClient(), new TranslationGenerator(generator.getPackOutput(), "en_us"));
    }
}