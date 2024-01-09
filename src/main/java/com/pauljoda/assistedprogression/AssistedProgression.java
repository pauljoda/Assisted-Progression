package com.pauljoda.assistedprogression;

import com.mojang.logging.LogUtils;
import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

/**
 * This is the main entry point for the AssistedProgression. It is responsible for initiating the loading,
 * registration, and event subscription operations of the mod.
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * @author Paul Davis - pauljoda
 * @since 6/3/2022
 */
@Mod(Reference.MOD_ID)
public class AssistedProgression {
    // Logger
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
    * AssistedProgression constructor. It is responsible for initiating the loading,
    * registration, and event subscription operations of the mod.
    * @param modEventBus The Forge mod event bus.
    */
    public AssistedProgression(IEventBus modEventBus) {
        // Setup registries
        Registration.init(modEventBus);
    }
}