package com.pauljoda.assistedprogression.common.items.container;

import com.mojang.datafixers.util.Pair;
import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.common.container.BaseContainer;
import com.pauljoda.nucleus.common.container.slots.PhantomSlot;
import com.pauljoda.nucleus.common.items.InventoryHandlerItem;
import com.pauljoda.nucleus.util.RenderUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

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
public class TrashBagContainer extends BaseContainer {

    public final ItemStack trashBag;

    public TrashBagContainer(int id,
                             Inventory playerInventory,
                             IItemHandler inventory,
                             ItemStack itemStack) {
        super(Registration.TRASH_BAG_CONTAINER.get(),
                id, playerInventory, inventory);

        trashBag = itemStack;

        // Add trash bag slots
        if(inventory.getSlots() == 1) { // Regular trash bag
            addSlot(new PhantomSlot(inventory, 0, 79, 32) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return true;
                }
            });
        } else  // Hefty Bag
            addInventoryGridPhantom(8, 32, 9);

        addPlayerInventorySlots(84);
    }

    /**
     * Used to add a grid of phantom slots a container
     *
     * @param offsetX The x start
     * @param offsetY The y start
     * @param width   How wide the grid is
     */
    private void addInventoryGridPhantom(int offsetX, int offsetY, int width) {
        int height = (int) Math.ceil((double) inventorySize / width);
        int slotId = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                addSlot(new PhantomSlot(inventory, slotId, offsetX + x * 18, offsetY + y * 18) {
                    @Override
                    public boolean mayPlace(@Nonnull ItemStack stack) {
                        return true;
                    }
                });
                slotId++;
            }
        }
    }

    /*******************************************************************************************************************
     * Container                                                                                                       *
     *******************************************************************************************************************/

    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);

        if (!playerIn.getLevel().isClientSide) {
            if (ItemStack.isSame(playerIn.getMainHandItem(), trashBag)) {
                ((InventoryHandlerItem) playerIn.getMainHandItem()
                        .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null))
                        .writeToNBT(trashBag.save(new CompoundTag()));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
