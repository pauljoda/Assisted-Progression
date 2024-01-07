package com.pauljoda.assistedprogression.common.items;

import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

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
public class NetItem extends BaseItem implements IAdvancedToolTipProvider {

    public NetItem() {
        super(64);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        if (!(level instanceof ServerLevel))
            return InteractionResult.SUCCESS;
        else {
            var stack = context.getItemInHand();
            // No Tag
            if (!stack.hasTag()) return InteractionResult.PASS;

            var pos = context.getClickedPos();
            var direction = context.getClickedFace();

            // If spawner frame, make spawner
            if(level.getBlockState(pos).is(Registration.SPAWNER_FRAME_BLOCK.get())) {
                level.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 3);
                if(level.getBlockEntity(pos) instanceof SpawnerBlockEntity spawner) {
                    var baseSpawner = spawner.getSpawner();
                    var tag = stack.getTag();
                    var entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryParse(tag.getString("type")));
                    baseSpawner.setEntityId(entityType, level, RandomSource.create(), pos);
                    spawner.setChanged();
                    level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 3);
                    if(!context.getPlayer().isCreative())
                        stack.shrink(1);
                    return InteractionResult.CONSUME;
                }
            }

            BlockPos spawnLocation;
            if(level.getBlockState(pos).getCollisionShape(level, pos).isEmpty())
                spawnLocation = pos;
            else
                spawnLocation = pos.relative(direction);

            var tag = stack.getTag();
            var entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryParse(tag.getString("type")));

            if(entityType != null) {
                var entity = entityType.spawn((ServerLevel) level, stack, context.getPlayer(),
                        spawnLocation, MobSpawnType.SPAWN_EGG, true,
                        !Objects.equals(pos, spawnLocation) && direction == Direction.UP);
                if(entity instanceof LivingEntity livingEntity) {
                    livingEntity.load(tag);
                    livingEntity.readAdditionalSaveData(tag);
                    livingEntity.setPos(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ());
                    if(!context.getPlayer().isCreative())
                        stack.shrink(1);
                    level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
                }
            }
            return InteractionResult.CONSUME;
        }
    }

    /*******************************************************************************************************************
     * Display Info                                                                                                    *
     *******************************************************************************************************************/

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.hasTag();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> toolTip, TooltipFlag advanced) {
        if (stack.hasTag()) {
            var tag = stack.getTag();
            var entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryParse(tag.getString("type")));
            var spawnType = I18n.get(entityType.toString());

            toolTip.add(Component.translatable(
                    ChatFormatting.GOLD + "" +
                            ChatFormatting.ITALIC +
                            ClientUtils.translate("assisted_progression.text.net.stored") +
                            spawnType));
        }
        super.appendHoverText(stack, level, toolTip, advanced);
    }
}