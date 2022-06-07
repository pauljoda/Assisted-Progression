package com.pauljoda.assistedprogression.data;

import com.pauljoda.assistedprogression.common.blocks.EnderPadBlock;
import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

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
public class BlockStateGenerator extends BlockStateProvider {

    public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Reference.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Ender Pad
        ModelFile baseModel =
                models().cubeColumn(Registration.ENDER_PAD_BLOCK.get().getRegistryName().getPath(),
                        modLoc("blocks/ender_pad"), modLoc("blocks/ender_pad_top"));
        getVariantBuilder(Registration.ENDER_PAD_BLOCK.get())
                // Set the rotation models
                .forAllStates(state ->
                        ConfiguredModel.builder()
                                .modelFile(baseModel)
                                .rotationY((int) state.getValue(EnderPadBlock.FACING).toYRot())
                                .build());

        // Player Plate
        var playerPlate = Registration.PLAYER_PLATE_BLOCK.get();
        var pressurePlate =
                models().pressurePlate(playerPlate.getRegistryName().getPath(), mcLoc("block/bricks"));
        var pressurePlateDown =
                models().pressurePlateDown(playerPlate.getRegistryName().getPath() + "_down", mcLoc("block/bricks"));
        getVariantBuilder(playerPlate)
                .partialState().with(PressurePlateBlock.POWERED, true).addModels(new ConfiguredModel(pressurePlateDown))
                .partialState().with(PressurePlateBlock.POWERED, false).addModels(new ConfiguredModel(pressurePlate));
    }
}