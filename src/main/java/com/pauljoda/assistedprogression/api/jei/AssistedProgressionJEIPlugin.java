package com.pauljoda.assistedprogression.api.jei;

import com.pauljoda.assistedprogression.api.jei.handler.TrashBagGhostIngredientHandler;
import com.pauljoda.assistedprogression.client.screen.TrashBagMenu;
import com.pauljoda.assistedprogression.lib.Reference;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.resources.ResourceLocation;

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
@JeiPlugin
public class AssistedProgressionJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Reference.MOD_ID, "jei");
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        // Create the ghost item handler for trash bags
        registration.addGhostIngredientHandler(TrashBagMenu.class, new TrashBagGhostIngredientHandler<>());
    }
}
