package com.pauljoda.assistedprogression.data;

import com.pauljoda.assistedprogression.common.blocks.EnderPadBlock;
import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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
public class BlockStateGenerator extends BlockStateProvider {

    public BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Reference.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Ender Pad
        ModelFile baseModel =
                models().cubeColumn(BuiltInRegistries.BLOCK.getKey(Registration.ENDER_PAD_BLOCK.get()).getPath(),
                        modLoc("block/ender_pad"), modLoc("block/ender_pad_top"));
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
                models().pressurePlate(BuiltInRegistries.BLOCK.getKey(Registration.PLAYER_PLATE_BLOCK.get()).getPath(), mcLoc("block/bricks"));
        var pressurePlateDown =
                models().pressurePlateDown(BuiltInRegistries.BLOCK.getKey(Registration.PLAYER_PLATE_BLOCK.get()).getPath() + "_down", mcLoc("block/bricks"));
        getVariantBuilder(playerPlate)
                .partialState().with(PressurePlateBlock.POWERED, true).addModels(new ConfiguredModel(pressurePlateDown))
                .partialState().with(PressurePlateBlock.POWERED, false).addModels(new ConfiguredModel(pressurePlate));

        simpleBlock(Registration.SPAWNER_FRAME_BLOCK.get());

        // Sun
        var sunModel =
                models().getBuilder("block/sun")
                        .parent(models().getExistingFile(mcLoc("block")))
                        .element().from(6F, 6F, 6F).to(10F, 10F, 10F)
                        .allFaces(((direction, faceBuilder) -> {
                            faceBuilder.texture("#sun");
                        }))
                        .end()
                        .texture("sun", modLoc("block/sun"))
                        .texture("all", modLoc("block/sun"))
                        .texture("particle", modLoc("block/sun"));
        getVariantBuilder(Registration.SUN_BLOCK.get()).partialState().setModels(new ConfiguredModel(sunModel));
    }
}