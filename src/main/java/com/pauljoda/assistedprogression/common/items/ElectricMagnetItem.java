package com.pauljoda.assistedprogression.common.items;

import com.pauljoda.nucleus.common.items.EnergyContainingItem;
import com.pauljoda.nucleus.util.ClientUtils;
import com.pauljoda.nucleus.util.EnergyUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
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
    // Max energy storage
    private static final int ENERGY_CAPACITY = 32000;

    // How much energy to drain per tick of use
    private static final int DRAIN_PER_TICK = 10;

    public ElectricMagnetItem() {
        super();
        // Set as less destructive
        isCheapMagnet = false;
    }

    @Override
    protected void onMagnetize(ItemStack stack) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(handler -> {
            handler.extractEnergy(DRAIN_PER_TICK, false);
        });
    }

    @Override
    protected boolean canMagnetize(ItemStack stack) {
        var hasEnergy = false;
        var energyStorage = stack.getCapability(CapabilityEnergy.ENERGY).orElse(new EnergyStorage(0));
        hasEnergy = energyStorage.getEnergyStored() > 0;
        return super.canMagnetize(stack) && hasEnergy;
    }

    /*******************************************************************************************************************
     * Item                                                                                                            *
     *******************************************************************************************************************/

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new EnergyContainingItem(stack, ENERGY_CAPACITY);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        var energyItem = stack.getCapability(CapabilityEnergy.ENERGY);
        if(energyItem.isPresent()) {
            var energyHandler = energyItem.orElse(null);
            return Math.min(13 * energyHandler.getEnergyStored() / energyHandler.getMaxEnergyStored(), 13);
        }
        return super.getBarWidth(stack);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return 16733525;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        var energyCapability = stack.getCapability(CapabilityEnergy.ENERGY);
        if(!energyCapability.isPresent())
            return super.isDamaged(stack);
        var energyStorage = energyCapability.orElse(null);
        return energyStorage.getEnergyStored() != energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(e -> e.getEnergyStored() != e.getMaxEnergyStored()).orElse(super.isBarVisible(stack));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level,
                                @NotNull List<Component> toolTip, @NotNull TooltipFlag advanced) {
        EnergyUtils.addToolTipInfo(stack, toolTip);
    }
}
