package com.pauljoda.assistedprogression.common.items;

import com.pauljoda.nucleus.capabilities.energy.EnergyBank;
import com.pauljoda.nucleus.util.EnergyUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
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
 * @since 6/5/2022
 */
public class ElectricMagnetItem extends MagnetItem {
    // Variables
    private ItemStack heldStack;
    private EnergyBank localEnergy;

    // Max energy storage
    public static final int ENERGY_CAPACITY = 32000;

    // How much energy to drain per tick of use
    private static final int DRAIN_PER_TICK = 10;

    public ElectricMagnetItem() {
        super();
        // Set as less destructive
        isCheapMagnet = false;
    }

    @Override
    protected void onMagnetize(ItemStack stack) {
        var energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if(energy != null)
            energy.extractEnergy(DRAIN_PER_TICK, false);
    }

    @Override
    protected boolean canMagnetize(ItemStack stack) {
        var hasEnergy = false;
        var energyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if(energyStorage == null) return false;
        hasEnergy = energyStorage.getEnergyStored() > 0;
        return super.canMagnetize(stack) && hasEnergy;
    }

    /*******************************************************************************************************************
     * Item                                                                                                            *
     *******************************************************************************************************************/

    @Override
    public int getBarWidth(ItemStack stack) {
        var energyItem = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if(energyItem != null) {
            return Math.min(13 * energyItem.getEnergyStored() / energyItem.getMaxEnergyStored(), 13);
        }
        return super.getBarWidth(stack);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return 16733525;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        var energyCapability = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if(energyCapability != null)
            return super.isDamaged(stack);
        return energyCapability.getEnergyStored() != energyCapability.getMaxEnergyStored();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        var e = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        return e == null ? super.isBarVisible(stack) : e.getEnergyStored() != e.getMaxEnergyStored();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level,
                                @NotNull List<Component> toolTip, @NotNull TooltipFlag advanced) {
        EnergyUtils.addToolTipInfo(stack, toolTip);
    }
}