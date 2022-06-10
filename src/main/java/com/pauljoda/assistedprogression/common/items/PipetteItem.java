package com.pauljoda.assistedprogression.common.items;

import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.client.gui.GuiColor;
import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.registries.ForgeRegistries;
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

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidHandlerItemStack(stack, FluidAttributes.BUCKET_VOLUME);
    }

    /*******************************************************************************************************************
     * Item                                                                                                            *
     *******************************************************************************************************************/

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if(!context.getLevel().isClientSide) {
            if(stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null).isPresent()
                    && context.getLevel().getBlockEntity(context.getClickedPos()) != null
                    && context.getLevel().getBlockEntity(context.getClickedPos())
                    .getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, context.getClickedFace()).isPresent()) {
                IFluidHandler fluidTile = context.getLevel().getBlockEntity(context.getClickedPos())
                        .getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, context.getClickedFace()).orElse(null);
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
        toolTip.add(new TextComponent(
                GuiColor.ORANGE +
                        ClientUtils.translate("assisted_progression.text.pipette.fluidStored")));
        if(stack.hasTag()) {
            FluidStack currentStored = FluidUtil.getFluidContained(stack).orElse(null);
            if(currentStored.isEmpty())
                return;

            toolTip.add(new TextComponent("  " +
                    currentStored.getDisplayName().getString()
                    + ": " + ClientUtils.formatNumber(currentStored.getAmount()) + " mb"));
        } else
            toolTip.add(new TextComponent("  "
                    + ChatFormatting.RED + ClientUtils.translate("assisted_progression.text.pipette.empty")));
    }

    @Override
    public void fillItemCategory(CreativeModeTab itemIn, NonNullList<ItemStack> tab) {
        if(itemIn == Registration.tabAssistedProgressionPipettes) {
            tab.add(new ItemStack(this));

            // Add for all fluids
            ForgeRegistries.FLUIDS.getValues().stream()
                    .filter(fluid -> fluid.isSource(fluid.defaultFluidState()))
                    .forEach(fluid -> {
                        ItemStack pipetteStack = new ItemStack(this);
                        FluidHandlerItemStack fluidStack = new FluidHandlerItemStack(pipetteStack, FluidAttributes.BUCKET_VOLUME);
                        if (fluidStack.fill(new FluidStack(fluid, FluidAttributes.BUCKET_VOLUME), IFluidHandler.FluidAction.EXECUTE)
                                == FluidAttributes.BUCKET_VOLUME)
                            tab.add(pipetteStack);
                    });
        }
    }
}
