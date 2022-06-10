package com.pauljoda.assistedprogression.client;

import com.pauljoda.assistedprogression.client.model.ModelPipette;
import com.pauljoda.assistedprogression.client.screen.TrashBagMenu;
import com.pauljoda.assistedprogression.common.items.container.TrashBagContainer;
import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 6/3/2022
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void setupClient(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Register Screens
            MenuScreens.register(Registration.TRASH_BAG_CONTAINER.get(), TrashBagMenu::new);

            // Set Clear block
            ItemBlockRenderTypes.setRenderLayer(Registration.SPAWNER_FRAME_BLOCK.get(), RenderType.cutout());
        });
    }

    /**
     * Stitch textures
     * @param event Event
     */
    @SubscribeEvent
    public static void textureStitch(TextureStitchEvent.Pre event) {
        // Need to add in the mask
        event.addSprite(ModelPipette.maskLocation);
    }

    @SubscribeEvent
    public static void modelBake(ModelBakeEvent event) {
        BakedModel baseModel = event.getModelRegistry().get(ModelPipette.LOCATION);
        event.getModelRegistry().put(ModelPipette.LOCATION,
                new ModelPipette.PipetteDynamicModel(event.getModelLoader(), baseModel, event.getModelLoader().GENERATION_MARKER.customData));
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Registration.NET_ENTITY.get(), ThrownItemRenderer::new);
    }
}
