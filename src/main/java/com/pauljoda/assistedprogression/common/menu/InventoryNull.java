package com.pauljoda.assistedprogression.common.items.container;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
public class InventoryNull implements IItemHandler, Container {

    @Override
    public int getSlots() {
        return 0;
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return ItemStack.EMPTY;
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return ItemStack.EMPTY;
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 0;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return false;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public @NotNull ItemStack getItem(int p_18941_) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItem(int p_18942_, int p_18943_) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int p_18951_) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int p_18944_, @NotNull ItemStack p_18945_) {

    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(@NotNull Player p_18946_) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}