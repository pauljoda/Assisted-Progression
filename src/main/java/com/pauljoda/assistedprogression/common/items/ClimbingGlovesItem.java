package com.pauljoda.assistedprogression.common.items;

import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
public class ClimbingGlovesItem extends BaseItem {

    private static final Vec3 STILL_VECTOR = new Vec3(0, 0, 0);

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level,
                              @NotNull Entity entity, int itemSlot, boolean isSelected) {
        // If a player on the client
        if(entity instanceof Player player && player.getLevel().isClientSide) {
            // If hitting a wall and not sneaking
            if(player.horizontalCollision) {
                // Get current movement
                var currentDelta = player.getDeltaMovement();
                // Add vertical movement
                player.setDeltaMovement(currentDelta.x,
                        player.isShiftKeyDown() ? 0.0D : (!player.getDeltaMovement().equals(STILL_VECTOR) ? 0.35D : currentDelta.y),
                        currentDelta.z);
            }
        }
    }
}
