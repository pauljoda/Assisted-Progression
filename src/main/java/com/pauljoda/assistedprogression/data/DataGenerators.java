package com.pauljoda.assistedprogression.data;

import com.pauljoda.assistedprogression.lib.Reference;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

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
        if(event.includeServer()) {
            // Recipes
            generator.addProvider(new RecipeGenerator(generator));
            // Block Tags
            BlockTagsProvider blockTagsProvider = new BlockTagGenerator(generator, event.getExistingFileHelper());
            generator.addProvider(blockTagsProvider);
            // Item Tags
            generator.addProvider(new ItemTagGenerator(generator, blockTagsProvider, event.getExistingFileHelper()));
            // Loot Tables
            generator.addProvider(new LootTableGenerator(generator));
        }

        // Generate Client Resources
        if(event.includeClient()) {
            // Block States
            generator.addProvider(new BlockStateGenerator(generator, event.getExistingFileHelper()));
            // Item Models
            generator.addProvider(new ItemModelGenerator(generator, event.getExistingFileHelper()));
            // Translation
            generator.addProvider(new TranslationGenerator(generator, "en_us"));
        }
    }
}
