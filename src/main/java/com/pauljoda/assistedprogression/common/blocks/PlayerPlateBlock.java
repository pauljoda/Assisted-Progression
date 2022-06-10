package com.pauljoda.assistedprogression.common.blocks;

import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
public class PlayerPlateBlock extends BasePressurePlateBlock implements IAdvancedToolTipProvider {

    public PlayerPlateBlock() {
        super(Properties.of(Material.METAL).strength(2.0F));
        registerDefaultState(getStateDefinition().any().setValue(PressurePlateBlock.POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PressurePlateBlock.POWERED);
    }

    /*******************************************************************************************************************
     * BlockBasePressurePlate                                                                                          *
     *******************************************************************************************************************/

    @Override
    protected void playOnSound(LevelAccessor level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS, 0.3F, 0.6F);
    }

    @Override
    protected void playOffSound(LevelAccessor level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundSource.BLOCKS, 0.3F, 0.6F);
    }

    @Override
    protected int getSignalStrength(Level level, BlockPos pos) {
        return level.getEntitiesOfClass(Player.class, BasePressurePlateBlock.TOUCH_AABB.move(pos)).size() >= 1 ? 15 : 0;
    }

    @Override
    protected int getSignalForState(BlockState state) {
        return state.getValue(PressurePlateBlock.POWERED) ? 15 : 0;
    }

    @Override
    protected BlockState setSignalForState(BlockState state, int strength) {
        return state.setValue(PressurePlateBlock.POWERED, strength > 0);
    }

    /*******************************************************************************************************************
     * IAdvancedToolTipProvided                                                                                        *
     *******************************************************************************************************************/

    /**
     * Get the tool tip to present when shift is pressed
     *
     * @param itemStack The itemstack
     * @return The list to display
     */
    @Nullable
    @Override
    public List<String> getAdvancedToolTip(@NotNull ItemStack itemStack) {
        return List.of(ChatFormatting.GREEN + ClientUtils.translate("player_player.desc"));
    }
}
