package com.pauljoda.assistedprogression.lib;

import com.pauljoda.assistedprogression.common.blocks.EnderPadBlock;
import com.pauljoda.assistedprogression.common.blocks.entity.EnderPadBlockEntity;
import com.pauljoda.assistedprogression.common.items.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
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

    /**
     * Registers a block entity
     * @param block The block to register an item for
     * @return The item for the block
     */
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tabAssistedProgression)));
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

    public static CreativeModeTab tabAssistedProgressionPipettes = new CreativeModeTab(Reference.MOD_ID + "_pipettes") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(PIPETTE_ITEM.get());
        }
    };

    public static final RegistryObject<Item> SPAWNER_RELOCATOR_ITEM =
            ITEMS.register("spawner_relocator", SpawnerRelocatorItem::new);

    public static final RegistryObject<Item> MAGNET_ITEM =
            ITEMS.register("magnet", MagnetItem::new);

    public static final RegistryObject<Item> ELECTRIC_MAGNET_ITEM =
            ITEMS.register("electric_magnet", ElectricMagnetItem::new);

    public static final RegistryObject<Item> PIPETTE_ITEM =
            ITEMS.register("pipette", PipetteItem::new);

    public static final RegistryObject<Item> PARASHOES_ITEM =
            ITEMS.register("parashoes", ParashoesItem::new);

    public static final RegistryObject<Item> CLIMBING_GLOVES_ITEM =
            ITEMS.register("climbing_gloves", ClimbingGlovesItem::new);

    /*******************************************************************************************************************
     * Blocks                                                                                                          *
     *******************************************************************************************************************/

    public static final RegistryObject<Block> ENDER_PAD_BLOCK =
            BLOCKS.register("ender_pad", EnderPadBlock::new);
    public static final RegistryObject<Item> ENDER_PAD_BLOCK_ITEM =  fromBlock(ENDER_PAD_BLOCK);

    /*******************************************************************************************************************
     * Block Entity                                                                                                    *
     *******************************************************************************************************************/

    public static final RegistryObject<BlockEntityType<EnderPadBlockEntity>> ENDER_PAD_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("ender_pad",
                    () -> BlockEntityType.Builder.of(EnderPadBlockEntity::new, ENDER_PAD_BLOCK.get
                            ()).build(null));
}
