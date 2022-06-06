package com.pauljoda.assistedprogression;

import com.mojang.logging.LogUtils;
import com.pauljoda.assistedprogression.client.ClientSetup;
import com.pauljoda.assistedprogression.common.events.PlayerFallEvent;
import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

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
@Mod(Reference.MOD_ID)
public class AssistedProgression {
    // Logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public AssistedProgression() {
        // Setup registries
        Registration.init();

        // Register us
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Setup client
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::setupClient));
    }

    private void setup(final FMLCommonSetupEvent event) { }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) { }
}
