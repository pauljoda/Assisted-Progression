package com.pauljoda.assistedprogression.common.items;

import com.pauljoda.assistedprogression.common.items.container.TrashBagContainer;
import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.common.items.InventoryHandlerItem;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;
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
 * @since 6/7/2022
 */
@Mod.EventBusSubscriber
public class TrashBagItem extends BaseItem {

    // The size of this inventory
    public final int bagInventorySize;

    public TrashBagItem(int size) {
        super(new Properties()
                .stacksTo(1)
                .tab(Registration.tabAssistedProgression));
        bagInventorySize = size;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new InventoryHandlerItem(stack, nbt) {
            @Override
            protected int getInventorySize() {
                return bagInventorySize;
            }

            @Override
            protected boolean isItemValidForSlot(int i, ItemStack itemStack) {
                return true;
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return true;
            }
        };
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player,
                                                           @NotNull InteractionHand hand) {
        if(!level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            MenuProvider menuProvider = new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return new TranslatableComponent(
                            bagInventorySize == 1 ?
                            Registration.TRASH_BAG_ITEM.get().getRegistryName().getPath() :
                            Registration.HEFTY_BAG_ITEM.get().getRegistryName().getPath());
                }

                @Override
                public @NotNull AbstractContainerMenu createMenu(int windowID, @NotNull Inventory playerInventory, Player player) {
                    return new TrashBagContainer(windowID, playerInventory,
                            player.getItemInHand(hand).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null),
                            player.getItemInHand(hand));
                }
            };
            NetworkHooks.openGui((ServerPlayer) player, menuProvider, player.getOnPos());
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }
        return super.use(level, player, hand);
    }

    /*******************************************************************************************************************
     * Event                                                                                                           *
     *******************************************************************************************************************/

    /**
     * Called when an item is picked up, here we test if we should void the items
     * @param event The item pickup event
     */
    @SubscribeEvent
    public static void onItemPickup(EntityItemPickupEvent event) {
        Player player = event.getPlayer();
        ItemStack pickedItem = event.getItem().getItem();

        if (pickedItem.isEmpty() || player == null) return;

        // Look for trashbags
        for (ItemStack stack : player.getInventory().items) {
            // If we have a valid trashbag
            if (!stack.isEmpty() && stack.getItem() instanceof TrashBagItem
                    && stack.hasTag()
                    && stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).isPresent()) {
                IItemHandler trashBagHandler
                        = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);

                // Check against filter
                for (int x = 0; x < trashBagHandler.getSlots(); x++) {
                    ItemStack trashKey = trashBagHandler.getStackInSlot(x);
                    if (!trashKey.isEmpty()) {
                        if (ItemStack.isSame(trashKey, pickedItem) &&
                                ItemStack.isSameItemSameTags(trashKey, pickedItem)) {
                            pickedItem.shrink(pickedItem.getCount());
                            player.getLevel().playSound(null,
                                    new BlockPos(player.getX(), player.getY(), player.getZ()),
                                    SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS ,
                                    0.3F, 0.5F);
                            return; // Items voided, no need to continue
                        }
                    }
                }
            }
        }
    }
}
