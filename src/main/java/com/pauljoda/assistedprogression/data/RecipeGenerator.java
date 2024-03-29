package com.pauljoda.assistedprogression.data;

import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

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

    public RecipeGenerator(PackOutput generatorIn, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(generatorIn, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput outputs) {
        // Items -------------------------------------------------------------------------------------------------------
        // Spawner Relocator
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.SPAWNER_RELOCATOR_ITEM.get())
                .define('e', Tags.Items.ENDER_PEARLS)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Tags.Items.SLIMEBALLS)
                .pattern("  s")
                .pattern(" i ")
                .pattern("e  ")
                .unlockedBy("has_ender_pearls", has(Tags.Items.ENDER_PEARLS))
                .save(outputs);

        // Cheap Magnet
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.MAGNET_ITEM.get())
                .pattern("I I")
                .pattern("I I")
                .pattern(" I ")
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(outputs);

        // Electric Magnet
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.ELECTRIC_MAGNET_ITEM.get())
                .pattern("D D")
                .pattern("IRI")
                .pattern(" I ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .unlockedBy("has_iron", has(Tags.Items.GEMS_DIAMOND))
                .save(outputs);

        // Pipette
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.PIPETTE_ITEM.get())
                .pattern("  P")
                .pattern(" P ")
                .pattern("G  ")
                .define('P', Tags.Items.GLASS_PANES)
                .define('G', Tags.Items.GLASS)
                .unlockedBy("has_glass", has(Tags.Items.GLASS_PANES))
                .save(outputs);

        // Parashoes
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.PARASHOES_ITEM.get())
                .pattern("F F")
                .pattern("SBS")
                .pattern("C C")
                .define('F', Tags.Items.FEATHERS)
                .define('S', Tags.Items.STRING)
                .define('B', Items.LEATHER_BOOTS)
                .define('C', Items.WHITE_CARPET)
                .unlockedBy("has_boots", has(Items.LEATHER_BOOTS))
                .save(outputs);

        // Climbing Gloves
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.CLIMBING_GLOVES_ITEM.get())
                .pattern(" L ")
                .pattern("LIL")
                .pattern("SLS")
                .define('L', Tags.Items.LEATHER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', Tags.Items.STRING)
                .unlockedBy("has_leather", has(Tags.Items.LEATHER))
                .save(outputs);

        // Trash Bag
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.TRASH_BAG_ITEM.get())
                .pattern("S S")
                .pattern("L L")
                .pattern("LLL")
                .define('S', Tags.Items.STRING)
                .define('L', Tags.Items.LEATHER)
                .unlockedBy("has_leather", has(Tags.Items.LEATHER))
                .save(outputs);

        // Hefty Bag
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.HEFTY_BAG_ITEM.get())
                .pattern("S S")
                .pattern("L L")
                .pattern("LLL")
                .define('S', Tags.Items.STRING)
                .define('L', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_leather", has(Tags.Items.INGOTS_IRON))
                .save(outputs);

        // Net
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.NET_ITEM.get())
                .pattern("S S")
                .pattern(" I ")
                .pattern("S S")
                .define('S', Tags.Items.STRING)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_string", has(Tags.Items.STRING))
                .save(outputs);

        // Launcher
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.NET_LAUNCHER_ITEM.get())
                .pattern("   ")
                .pattern("IIB")
                .pattern(" IR")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .unlockedBy("has_redstone", has(Tags.Items.STORAGE_BLOCKS_REDSTONE))
                .save(outputs);

        // Blocks ------------------------------------------------------------------------------------------------------
        // Ender Pad
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.ENDER_PAD_BLOCK_ITEM.get())
                .pattern("ISI")
                .pattern("SES")
                .pattern("ISI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', Tags.Items.STONE)
                .define('E', Tags.Items.ENDER_PEARLS)
                .unlockedBy("has_pearls", has(Tags.Items.ENDER_PEARLS))
                .save(outputs);

        // Player Plate
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.PLAYER_PLATE_BLOCK.get())
                .pattern("  ")
                .pattern("BB")
                .define('B', Tags.Items.INGOTS_BRICK)
                .unlockedBy("has_brick", has(Tags.Items.INGOTS_BRICK))
                .save(outputs);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.SPAWNER_FRAME_BLOCK.get())
                .pattern("ODO")
                .pattern("IEI")
                .pattern("ODO")
                .define('O', Tags.Items.OBSIDIAN)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('E', Items.END_CRYSTAL)
                .unlockedBy("has_endcrystal", has(Items.END_CRYSTAL))
                .save(outputs);

        // Sun Block
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.SUN_BLOCK.get())
                .pattern("GMG")
                .pattern("MDM")
                .pattern("GMG")
                .define('G', Tags.Items.DUSTS_GLOWSTONE)
                .define('M', Items.BONE_MEAL)
                .define('D', Items.LANTERN)
                .unlockedBy("hasLantern", has(Items.LANTERN))
                .save(outputs);
    }
}