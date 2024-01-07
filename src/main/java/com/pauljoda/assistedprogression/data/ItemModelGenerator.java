package com.pauljoda.assistedprogression.data;

import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

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
public class ItemModelGenerator extends ItemModelProvider {

    public ItemModelGenerator(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, Reference.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerModels() {
        // Items -------------------------------------------------------------------------------------------------------
        // Spawner Relocator
        basicItem(Registration.SPAWNER_RELOCATOR_ITEM.get());

        // Magnet
        basicItem(Registration.MAGNET_ITEM.get());

        // Electric Magnet
        basicItem(Registration.ELECTRIC_MAGNET_ITEM.get());

        // Parashoes
        basicItem(Registration.PARASHOES_ITEM.get());

        // Climbing Gloves
        basicItem(Registration.CLIMBING_GLOVES_ITEM.get());

        // Trash Bag
        basicItem(Registration.TRASH_BAG_ITEM.get());

        // Hefty Bag
        basicItem(Registration.HEFTY_BAG_ITEM.get());

        // Net
        basicItem(Registration.NET_ITEM.get());

        // Net Launcher
        basicItem(Registration.NET_LAUNCHER_ITEM.get());

        // Blocks ------------------------------------------------------------------------------------------------------

        // Ender Pad
        fromBlock(Registration.ENDER_PAD_BLOCK.get());

        // Player Plate
        fromBlock(Registration.PLAYER_PLATE_BLOCK.get());

        // Spawner Frame
        fromBlock(Registration.SPAWNER_FRAME_BLOCK.get());

        // Sun
        fromBlock(Registration.SUN_BLOCK.get());
    }

    public void fromBlock(Block block) {
        withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath(),
                modLoc(String.format("block/%s", BuiltInRegistries.BLOCK.getKey(block).getPath())));
    }
}