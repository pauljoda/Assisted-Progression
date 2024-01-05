package com.pauljoda.assistedprogression.common.items;

import com.pauljoda.assistedprogression.common.menu.TrashBagMenu;
import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.entity.player.EntityItemPickupEvent;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

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
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class TrashBagItem extends BaseItem {

    // The size of this inventory
    public final int bagInventorySize;

    public TrashBagItem(int size) {
        super(new Properties()
                .stacksTo(1));
        bagInventorySize = size;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player,
                                                           @NotNull InteractionHand hand) {
        if(!level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            MenuProvider menuProvider = new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return Component.translatable(
                            bagInventorySize == 1 ?
                                    BuiltInRegistries.ITEM.getKey(Registration.TRASH_BAG_ITEM.get()).getPath() :
                                    BuiltInRegistries.ITEM.getKey(Registration.HEFTY_BAG_ITEM.get()).getPath() );
                }

                @Override
                public @NotNull AbstractContainerMenu createMenu(int windowID, @NotNull Inventory playerInventory, Player player) {
                    return new TrashBagMenu(windowID, playerInventory,
                            player.getItemInHand(hand).getCapability(Capabilities.ItemHandler.ITEM),
                            player.getItemInHand(hand));
                }
            };
            player.openMenu(menuProvider, player.getOnPos());
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
        Player player = event.getEntity();
        ItemStack pickedItem = event.getItem().getItem();

        if (pickedItem.isEmpty() || player == null) return;

        // Look for trashbags
        for (ItemStack stack : player.getInventory().items) {
            // If we have a valid trashbag
            if (!stack.isEmpty() && stack.getItem() instanceof TrashBagItem
                    && stack.hasTag()
                    && stack.getCapability(Capabilities.ItemHandler.ITEM, null) != null) {
                IItemHandler trashBagHandler
                        = stack.getCapability(Capabilities.ItemHandler.ITEM, null);

                // Check against filter
                for (int x = 0; x < trashBagHandler.getSlots(); x++) {
                    ItemStack trashKey = trashBagHandler.getStackInSlot(x);
                    if (!trashKey.isEmpty()) {
                        if (ItemStack.isSameItem(trashKey, pickedItem) &&
                                ItemStack.isSameItemSameTags(trashKey, pickedItem)) {
                            pickedItem.shrink(pickedItem.getCount());
                            player.level().playSound(null,
                                    new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ()),
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