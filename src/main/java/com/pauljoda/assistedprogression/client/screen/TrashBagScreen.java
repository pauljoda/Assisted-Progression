package com.pauljoda.assistedprogression.client.screen;

import com.pauljoda.assistedprogression.common.menu.TrashBagContainer;
import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.client.gui.MenuBase;
import com.pauljoda.nucleus.client.gui.widget.display.MenuWidgetText;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

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
public class TrashBagScreen extends MenuBase<TrashBagContainer> {

    public TrashBagContainer trashBagContainer;

    public TrashBagScreen(TrashBagContainer inventory, Inventory playerInventory, Component title) {
        super(inventory, playerInventory, title, 175, 165,
                new ResourceLocation(Reference.MOD_ID, "textures/gui/" +
                        (inventory.trashBag.getItem() == Registration.TRASH_BAG_ITEM.get() ?
                                "trash_bag.png" : "hefty_bag.png")));
        trashBagContainer = inventory;
    }

    @Override
    protected void addComponents() {
        components.add(new MenuWidgetText(this, 7, 20, "Filters", null));
        components.add(new MenuWidgetText(this, 7, 73, "Inventory", null));
    }
}