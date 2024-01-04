package com.pauljoda.assistedprogression;


import com.mojang.logging.LogUtils;
import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
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

    public AssistedProgression(IEventBus modEventBus) {
        // Setup registries
        Registration.init(modEventBus);

        // Register Packets
        //PacketManager.initPackets();
    }
}