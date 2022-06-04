package com.pauljoda.assistedprogression.lib;

import com.pauljoda.assistedprogression.common.items.SpawnerRelocatorItem;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

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
public class Registration {
    /*******************************************************************************************************************
     * Registries                                                                                                      *
     *******************************************************************************************************************/

    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Reference.MOD_ID);
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS
            = DeferredRegister.create(ForgeRegistries.CONTAINERS, Reference.MOD_ID);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        BLOCK_ENTITY_TYPES.register(bus);
        ITEMS.register(bus);
        CONTAINERS.register(bus);
    }

    /*******************************************************************************************************************
     * Items                                                                                                           *
     *******************************************************************************************************************/

    public static CreativeModeTab tabAssistedProgression = new CreativeModeTab(Reference.MOD_ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(SPAWNER_RELOCATOR_ITEM.get());
        }
    };

    public static final RegistryObject<Item> SPAWNER_RELOCATOR_ITEM =
            ITEMS.register("spawner_relocator", SpawnerRelocatorItem::new);
}
