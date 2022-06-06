package com.pauljoda.assistedprogression.common.events;

import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
@Mod.EventBusSubscriber
public class PlayerFallEvent {

    @SubscribeEvent
    public static void playerFall(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof Player player &&
                player.getLevel().isClientSide) {
            if (!player.isFallFlying() &&
                    !player.getInventory().armor.get(0).isEmpty() &&
                    player.getInventory().armor.get(0).getItem() == Registration.PARASHOES_ITEM.get() &&
                    player.fallDistance > 2 &&
                    player.getDeltaMovement().y < -0.35D) {
                player.setDeltaMovement(player.getDeltaMovement().x, -0.35D, player.getDeltaMovement().z);
            }
        }
    }

    @SubscribeEvent
    public static void playerFallDamage(LivingFallEvent event) {
        if (event.getEntityLiving() instanceof Player player &&
                !player.getLevel().isClientSide &&
                !player.getInventory().armor.get(0).isEmpty() &&
                player.getInventory().armor.get(0).getItem() == Registration.PARASHOES_ITEM.get() &&
                event.getDistance() >= 2.0F) {
            event.setDistance(0.0F);
            player.getInventory().armor.get(0).hurtAndBreak(1, player, (stack) -> {
            });
        }
    }
}
