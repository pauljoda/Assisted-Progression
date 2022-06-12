package com.pauljoda.assistedprogression.data;

import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

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

    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Reference.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerModels() {
        // Items -------------------------------------------------------------------------------------------------------
        // Spawner Relocator
        singleTexture(Registration.SPAWNER_RELOCATOR_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("items/spawner_relocator"));

        // Magnet
        singleTexture(Registration.MAGNET_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("items/magnet"));

        // Electric Magnet
        singleTexture(Registration.ELECTRIC_MAGNET_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("items/electro_magnet"));

        // Parashoes
        singleTexture(Registration.PARASHOES_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("items/parashoes"));

        // Climbing Gloves
        singleTexture(Registration.CLIMBING_GLOVES_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("items/climbing_gloves"));

        // Trash Bag
        singleTexture(Registration.TRASH_BAG_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("items/trash_bag"));

        // Hefty Bag
        singleTexture(Registration.HEFTY_BAG_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("items/hefty_bag"));

        // Net
        singleTexture(Registration.NET_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("items/net"));

        // Net Launcher
        singleTexture(Registration.NET_LAUNCHER_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("items/net_launcher"));

        // Blocks ------------------------------------------------------------------------------------------------------

        // Ender Pad
        createItemBlock(Registration.ENDER_PAD_BLOCK, "block/ender_pad");

        // Player Plate
        createItemBlock(Registration.PLAYER_PLATE_BLOCK, "block/player_plate");

        // Spawner Frame
        createItemBlock(Registration.SPAWNER_FRAME_BLOCK, "block/spawner_frame");

        // Sun
        createItemBlock(Registration.SUN_BLOCK, "block/sun");
    }

    @SuppressWarnings("ConstantConditions")
    protected void createItemBlock(RegistryObject<Block> block, String modelLocation) {
        withExistingParent(block.get().getRegistryName().getPath(), modLoc(modelLocation));
    }
}
