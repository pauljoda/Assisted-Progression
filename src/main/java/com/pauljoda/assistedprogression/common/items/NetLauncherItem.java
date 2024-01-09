package com.pauljoda.assistedprogression.common.items;

import com.pauljoda.assistedprogression.common.entities.NetEntity;
import com.pauljoda.nucleus.common.items.EnergyContainingItem;
import com.pauljoda.nucleus.util.EnergyUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
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
 * @since 6/10/2022
 */
public class NetLauncherItem extends BaseItem {
    // Max energy storage
    public static final int ENERGY_CAPACITY = 32000;

    protected @Nullable ItemStack getAmmo(Player player) {
        for (ItemStack stack :
                player.getInventory().items) {
            if (stack.getItem() instanceof NetItem && !stack.hasTag())
                return stack;
        }
        return null;
    }

    /*******************************************************************************************************************
     * Item                                                                                                            *
     *******************************************************************************************************************/

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 7200;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        // If we have ammo
        var ammo = getAmmo(player);
        if(ammo != null) {
            player.startUsingItem(hand);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
        }

        return super.use(level, player, hand);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity,
                             int timeLeft) {
        if (timeLeft <= 7180 && livingEntity instanceof Player player) {
            var energyItem = stack.getCapability(Capabilities.EnergyStorage.ITEM);
            if(energyItem != null) {
                var requiredEnergy = ENERGY_CAPACITY / 8;
                var ammo = getAmmo(player);
                if((energyItem.getEnergyStored() >= requiredEnergy && ammo != null)
                        || player.isCreative()) {
                    // Spawn net and launch
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.CROSSBOW_SHOOT, SoundSource.NEUTRAL, 0.5F, 0.6F);

                    if(!level.isClientSide) {
                        var netEntity = new NetEntity(level, player);
                        netEntity.setItem(ammo);
                        netEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                        level.addFreshEntity(netEntity);
                    }

                    if(!player.isCreative()) {
                        ammo.shrink(1);
                        energyItem.extractEnergy(ENERGY_CAPACITY / 8, false);
                    }
                }
            }
        }
    }

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
        if(energyCapability == null)
            return super.isDamaged(stack);
        return energyCapability.getEnergyStored() != energyCapability.getMaxEnergyStored();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        var energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        return energy == null ? super.isBarVisible(stack) : energy.getEnergyStored() != energy.getMaxEnergyStored();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level,
                                @NotNull List<Component> toolTip, @NotNull TooltipFlag advanced) {
        EnergyUtils.addToolTipInfo(stack, toolTip);
    }
}