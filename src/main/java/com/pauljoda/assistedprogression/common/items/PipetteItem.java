package com.pauljoda.assistedprogression.common.items;

import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.client.gui.GuiColor;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
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
 * @since 6/6/2022
 */
public class PipetteItem extends BaseItem {

    /*******************************************************************************************************************
     * Item                                                                                                            *
     *******************************************************************************************************************/

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if(!context.getLevel().isClientSide) {
            if(stack.getCapability(Capabilities.FluidHandler.ITEM, null) != null
                    && context.getLevel().getBlockEntity(context.getClickedPos()) != null
                    && context.getLevel().getCapability(Capabilities.FluidHandler.BLOCK, context.getClickedPos(), context.getClickedFace()) != null) {
                IFluidHandler fluidTile = context.getLevel().getCapability(Capabilities.FluidHandler.BLOCK, context.getClickedPos(), context.getClickedFace());
                if(FluidUtil.interactWithFluidHandler(context.getPlayer(), context.getHand(), fluidTile)) {
                    context.getLevel().sendBlockUpdated(context.getClickedPos()
                            , context.getLevel().getBlockState(context.getClickedPos()),
                            context.getLevel().getBlockState(context.getClickedPos()), 3);
                    return  InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> toolTip,
                                @NotNull TooltipFlag advanced) {
        toolTip.add(Component.translatable(
                GuiColor.ORANGE +
                        ClientUtils.translate("assisted_progression.text.pipette.fluidStored")));
        if(stack.hasTag()) {
            FluidStack currentStored = FluidUtil.getFluidContained(stack).orElse(null);
            if(currentStored.isEmpty())
                return;

            toolTip.add(Component.translatable("  " +
                    currentStored.getDisplayName().getString()
                    + ": " + ClientUtils.formatNumber(currentStored.getAmount()) + " mb"));
        } else
            toolTip.add(Component.translatable("  "
                    + ChatFormatting.RED + ClientUtils.translate("assisted_progression.text.pipette.empty")));
    }
}