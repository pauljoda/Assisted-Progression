package com.pauljoda.assistedprogression.data;

import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

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
public class RecipeGenerator extends RecipeProvider {

    public RecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        // Items -------------------------------------------------------------------------------------------------------
        // Spawner Relocator
        ShapedRecipeBuilder
                .shaped(Registration.SPAWNER_RELOCATOR_ITEM.get())
                .define('e', Tags.Items.ENDER_PEARLS)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Tags.Items.SLIMEBALLS)
                .pattern("  s")
                .pattern(" i ")
                .pattern("e  ")
                .unlockedBy("has_ender_pearls", has(Tags.Items.ENDER_PEARLS))
                .save(consumer);

        // Cheap Magnet
        ShapedRecipeBuilder
                .shaped(Registration.MAGNET_ITEM.get())
                .pattern("I I")
                .pattern("I I")
                .pattern(" I ")
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(consumer);

        // Electric Magnet
        ShapedRecipeBuilder
                .shaped(Registration.ELECTRIC_MAGNET_ITEM.get())
                .pattern("D D")
                .pattern("IRI")
                .pattern(" I ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .unlockedBy("has_iron", has(Tags.Items.GEMS_DIAMOND))
                .save(consumer);

        // Pipette
        ShapedRecipeBuilder
                .shaped(Registration.PIPETTE_ITEM.get())
                .pattern("  P")
                .pattern(" P ")
                .pattern("G  ")
                .define('P', Tags.Items.GLASS_PANES)
                .define('G', Tags.Items.GLASS)
                .unlockedBy("has_glass", has(Tags.Items.GLASS_PANES))
                .save(consumer);

        // Parashoes
        ShapedRecipeBuilder
                .shaped(Registration.PARASHOES_ITEM.get())
                .pattern("F F")
                .pattern("SBS")
                .pattern("C C")
                .define('F', Tags.Items.FEATHERS)
                .define('S', Tags.Items.STRING)
                .define('B', Items.LEATHER_BOOTS)
                .define('C', Items.WHITE_CARPET)
                .unlockedBy("has_boots", has(Items.LEATHER_BOOTS))
                .save(consumer);

    }
}
