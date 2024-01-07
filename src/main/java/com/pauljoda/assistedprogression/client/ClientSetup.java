package com.pauljoda.assistedprogression.client;

import com.pauljoda.assistedprogression.client.model.ModelPipette;
import com.pauljoda.assistedprogression.client.screen.TrashBagScreen;
import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.helper.ModelHelper;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.geometry.StandaloneGeometryBakingContext;

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

     @SubscribeEvent
     public static void modifyBaked(ModelEvent.ModifyBakingResult event) {
       var bakedModel = event.getModels().get(ModelPipette.LOCATION);
       event.getModels().put(ModelPipette.LOCATION,
               new ModelPipette.PipetteDynamicModel(event.getModelBakery(),
                       bakedModel,
                       StandaloneGeometryBakingContext.builder()
                               .withTransforms(ModelHelper.DEFAULT_ITEM_STATE)
                               .build(new ResourceLocation(Reference.MOD_ID, "pipette_transforms"))));
     }
//
//    @SubscribeEvent
//    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
//        event.registerEntityRenderer(Registration.NET_ENTITY.get(), ThrownItemRenderer::new);
//    }
}