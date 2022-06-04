package com.pauljoda.assistedprogression.common.items;

import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.world.item.Item;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 6/4/2022
 */
public class SpawnerRelocatorItem extends Item {

    public SpawnerRelocatorItem() {
        super(new Properties()
                .stacksTo(1)
                .tab(Registration.tabAssistedProgression));
    }
}
