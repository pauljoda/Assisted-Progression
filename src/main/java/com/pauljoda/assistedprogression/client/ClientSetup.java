package com.pauljoda.assistedprogression.client;

import com.pauljoda.assistedprogression.client.screen.TrashBagScreen;
import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

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
    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Register Screens
            MenuScreens.register(Registration.TRASH_BAG_CONTAINER.get(), TrashBagScreen::new);

            // Set Clear block
            //ItemBlockRenderTypes.setRenderLayer(Registration.SPAWNER_FRAME_BLOCK.get(), RenderType.cutout());
        });
    }

    /**
     * Stitch textures
     * @param event Event
     */
//    @SubscribeEvent
//    public static void textureStitch(TextureAtlasStitchedEvent event) {
//        // Need to add in the mask
//        event.addSprite(ModelPipette.maskLocation);
//    }

//    @SubscribeEvent
//    public static void modelBake(ModelEvent.RegisterAdditional event) {
//        BakedModel baseModel = event.getModelRegistry().get(ModelPipette.LOCATION);
//        event.getModelRegistry().put(ModelPipette.LOCATION,
//                new ModelPipette.PipetteDynamicModel(event.getModelLoader(), baseModel, event.getModelLoader().GENERATION_MARKER.customData));
//    }
//
//    @SubscribeEvent
//    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
//        event.registerEntityRenderer(Registration.NET_ENTITY.get(), ThrownItemRenderer::new);
//    }
}