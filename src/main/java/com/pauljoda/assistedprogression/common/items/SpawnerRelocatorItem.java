package com.pauljoda.assistedprogression.common.items;

import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.client.gui.GuiTextFormat;
import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.phys.HitResult;
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
 * @since 6/4/2022
 */
public class SpawnerRelocatorItem extends Item implements IAdvancedToolTipProvider {

    public SpawnerRelocatorItem() {
        super(new Properties()
                .stacksTo(1)
                .tab(Registration.tabAssistedProgression));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 7200;
    }

    /*******************************************************************************************************************
     * Item                                                                                                            *
     *******************************************************************************************************************/

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        player.startUsingItem(hand);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity, int timeLeft) {
        if (timeLeft <= 7180 && livingEntity instanceof Player player) {
            // Get what the player is looking at
            var blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);

            // If it is a block
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                // Get the location of the hit
                var pos = new BlockPos(blockHitResult.getBlockPos());

                // If no tag, try and pick up spawner, otherwise place
                if (!stack.hasTag()) {
                    // Check if we are looking at spawner
                    var blockEntity = level.getBlockEntity(pos);
                    if (blockEntity instanceof SpawnerBlockEntity spawnerBlockEntity) {
                        var tag = spawnerBlockEntity.getUpdateTag();
                        stack.setTag(tag);
                        level.removeBlock(pos, false);
                    }
                } else {
                    var newPosition = pos.relative(blockHitResult.getDirection());
                    if (!level.isEmptyBlock(newPosition)) return;

                    var tag = stack.getTag();
                    tag.putInt("x", newPosition.getX());
                    tag.putInt("y", newPosition.getY());
                    tag.putInt("z", newPosition.getZ());
                    level.setBlock(newPosition, Blocks.SPAWNER.defaultBlockState(), 11);
                    var spawnerBlockEntity = (SpawnerBlockEntity) level.getBlockEntity(newPosition);
                    spawnerBlockEntity.load(tag);
                    stack.setTag(null);

                    // If not creative, break item
                    if(!player.isCreative())
                        stack.shrink(1);
                }
            }
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
            var spawnData = tag.getCompound("SpawnData");
            var entityType = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(spawnData.getCompound("entity").getString("id")));
            var spawnType = I18n.get(entityType.toString());

            toolTip.add(new TranslatableComponent(
                    I18n.get("assisted_progression.text.spawnerRelocator.type",
                            ChatFormatting.GOLD,
                            ChatFormatting.ITALIC,
                            spawnType)
            ));
        }
        super.appendHoverText(stack, level, toolTip, advanced);
    }

    /*******************************************************************************************************************
     * IAdvancedToolTipProvider                                                                                        *
     *******************************************************************************************************************/

    @Nullable
    @Override
    public List<String> getAdvancedToolTip(@NotNull ItemStack itemStack) {
        return List.of(ClientUtils.translate("spawner_relocator.desc"));
    }
}
