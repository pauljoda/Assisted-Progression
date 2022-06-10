package com.pauljoda.assistedprogression.common.entity;

import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

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
public class NetEntity extends ThrowableItemProjectile {

    public NetEntity(EntityType<? extends ThrowableItemProjectile> entityType,
                     Level level) {
        super(entityType, level);
    }

    public NetEntity(Level level, LivingEntity entity) {
        super(Registration.NET_ENTITY.get(), entity, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return Registration.NET_ITEM.get();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        if(!level.isClientSide) {
            var stack = new ItemStack(Registration.NET_ITEM.get(), 1);
            var entity = result.getEntity();

            // If hit a valid target, add tag
            if(entity instanceof LivingEntity livingEntity &&
                    !(entity instanceof EnderDragon) &&
                    !(entity instanceof WitherBoss)) {
                var tag = new CompoundTag();
                livingEntity.save(tag);
                livingEntity.addAdditionalSaveData(tag);
                tag.putString("type", livingEntity.getType().getRegistryName().toString());
                livingEntity.remove(RemovalReason.KILLED);

                stack.setTag(tag);
            }

            // Spawn item, with or without tag
            var itemEntity = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), stack);
            level.addFreshEntity(itemEntity);
        }
        this.kill();
    }
}
