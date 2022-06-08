package com.pauljoda.assistedprogression.api.jei.handler;

import com.pauljoda.assistedprogression.client.screen.TrashBagMenu;
import com.pauljoda.assistedprogression.network.PacketManager;
import com.pauljoda.assistedprogression.network.packets.NotifyServerOfTrashBagChanges;
import com.pauljoda.nucleus.common.container.slots.IPhantomSlot;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 6/8/2022
 */
public class TrashBagGhostIngredientHandler<T extends TrashBagMenu> implements IGhostIngredientHandler<T> {

    /**
     * Called when a player wants to drag an ingredient on to your gui.
     * Return the targets that can accept the ingredient.
     * <p>
     * This is called when a player hovers over an ingredient with doStart=false,
     * and called again when they pick up the ingredient with doStart=true.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <I> List<Target<I>> getTargets(T gui, I ingredient, boolean doStart) {
        List<Target<I>> targets = new ArrayList<>();
        if(ingredient instanceof ItemStack) {
            for(Slot slot : gui.trashBagContainer.slots) {
                if(slot instanceof IPhantomSlot)
                    targets.add(new SlotTarget(gui, slot));
            }
        }
        return targets;
    }

    /**
     * Called when the player is done dragging an ingredient.
     * If the drag succeeded, {@link Target#accept(Object)} was called before this.
     * Otherwise, the player failed to drag an ingredient to a {@link Target}.
     */
    @Override
    public void onComplete() {

    }

    private class SlotTarget implements Target {

        private T guiParent;
        private Slot slot;

        SlotTarget(T guiParent, Slot slotIn) {
            this.guiParent = guiParent;
            this.slot = slotIn;
        }

        /**
         * @return the area (in screen coordinates) where the ingredient can be dropped.
         */
        @Override
        public Rect2i getArea() {
            return new Rect2i(guiParent.getGuiLeft() + slot.x,
                    guiParent.getGuiTop() + slot.y, 16, 16);
        }

        /**
         * Called with the ingredient when it is dropped on the target.
         */
        @Override
        public void accept(Object ingredient) {
            if(ingredient instanceof ItemStack) {
                slot.set((ItemStack) ingredient);
                PacketManager.INSTANCE
                        .sendToServer(new NotifyServerOfTrashBagChanges(guiParent.trashBagContainer.trashBag.getTag()));
            }
        }
    }
}