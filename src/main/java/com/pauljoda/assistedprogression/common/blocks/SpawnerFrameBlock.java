package com.pauljoda.assistedprogression.common.blocks;

import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
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
 * @since 6/10/2022
 */
public class SpawnerFrameBlock extends Block implements IAdvancedToolTipProvider {

    public SpawnerFrameBlock() {
        super(Properties.of().strength(2.0F).noOcclusion());
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
        return List.of(ChatFormatting.GREEN + ClientUtils.translate("spawner_frame.desc"));
    }
}