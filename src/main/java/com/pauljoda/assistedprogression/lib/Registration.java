package com.pauljoda.assistedprogression.lib;

import com.pauljoda.assistedprogression.common.blocks.EnderPadBlock;
import com.pauljoda.assistedprogression.common.blocks.PlayerPlateBlock;
import com.pauljoda.assistedprogression.common.blocks.SpawnerFrameBlock;
import com.pauljoda.assistedprogression.common.blocks.SunBlock;
import com.pauljoda.assistedprogression.common.blocks.blockentity.EnderPadBlockEntity;
import com.pauljoda.assistedprogression.common.blocks.blockentity.SunBlockEntity;
import com.pauljoda.assistedprogression.common.items.*;
import com.pauljoda.assistedprogression.common.menu.TrashBagMenu;
import com.pauljoda.nucleus.common.items.EnergyContainingItem;
import com.pauljoda.nucleus.common.items.InventoryHandlerItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * <a href="http://creativecommons.org/licenses/by-nc-sa/4.0/">License</a>
 *
 * @author Paul Davis - pauljoda
 * @since 6/3/2022
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registration {
    /*******************************************************************************************************************
     * Registries                                                                                                      *
     *******************************************************************************************************************/

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.createBlocks(Reference.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Reference.MOD_ID);
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.createItems(Reference.MOD_ID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS
            = DeferredRegister.create(Registries.MENU, Reference.MOD_ID);

    private static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Reference.MOD_ID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MOD_ID);


    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
        BLOCK_ENTITY_TYPES.register(bus);
        ITEMS.register(bus);
        CONTAINERS.register(bus);
        ENTITIES.register(bus);
        CREATIVE_MODE_TABS.register(bus);
    }

    /*******************************************************************************************************************
     * Items                                                                                                           *
     *******************************************************************************************************************/

    public static final DeferredHolder<Item, SpawnerRelocatorItem> SPAWNER_RELOCATOR_ITEM =
            ITEMS.register("spawner_relocator", SpawnerRelocatorItem::new);

    public static final DeferredHolder<Item, MagnetItem> MAGNET_ITEM =
            ITEMS.register("magnet", MagnetItem::new);

    public static final DeferredHolder<Item, ElectricMagnetItem> ELECTRIC_MAGNET_ITEM =
            ITEMS.register("electric_magnet", ElectricMagnetItem::new);

    public static final DeferredHolder<Item, PipetteItem> PIPETTE_ITEM =
            ITEMS.register("pipette", PipetteItem::new);
    //
//    public static final DeferredItem<Item> PARASHOES_ITEM =
//            ITEMS.register("parashoes", ParashoesItem::new);
//
    public static final DeferredHolder<Item, ClimbingGlovesItem> CLIMBING_GLOVES_ITEM =
            ITEMS.register("climbing_gloves", ClimbingGlovesItem::new);

    public static final DeferredHolder<Item, TrashBagItem> TRASH_BAG_ITEM =
            ITEMS.register("trash_bag", () -> new TrashBagItem(1));

    public static final DeferredHolder<Item, TrashBagItem> HEFTY_BAG_ITEM =
            ITEMS.register("hefty_bag", () -> new TrashBagItem(18));

//    public static final DeferredItem<Item> NET_ITEM =
//            ITEMS.register("net", NetItem::new);
//
//    public static final DeferredItem<Item> NET_LAUNCHER_ITEM =
//            ITEMS.register("net_launcher", NetLauncherItem::new);

    /*******************************************************************************************************************
     * Blocks                                                                                                          *
     *******************************************************************************************************************/

    public static final DeferredHolder<Block, EnderPadBlock> ENDER_PAD_BLOCK =
            BLOCKS.register("ender_pad",
                    () -> new EnderPadBlock(BlockBehaviour.Properties.of().strength(2.0F)));
    public static final DeferredHolder<Item, BlockItem> ENDER_PAD_BLOCK_ITEM =
            ITEMS.register("ender_pad", () -> new BlockItem(ENDER_PAD_BLOCK.get(), new Item.Properties()));

    public static final DeferredHolder<Block, PlayerPlateBlock> PLAYER_PLATE_BLOCK =
            BLOCKS.register("player_plate", () -> new PlayerPlateBlock(BlockBehaviour.Properties.of().strength(2.0f)));
    public static final DeferredHolder<Item, BlockItem> PLAYER_PLATE_BLOCK_ITEM =
            ITEMS.register("player_plate", () -> new BlockItem(PLAYER_PLATE_BLOCK.get(), new Item.Properties()));

    public static final DeferredHolder<Block, SpawnerFrameBlock> SPAWNER_FRAME_BLOCK =
            BLOCKS.register("spawner_frame", SpawnerFrameBlock::new);
    public static final DeferredHolder<Item, BlockItem> SPAWNER_FRAME_BLOCK_ITEM =
            ITEMS.register("spawner_frame", () -> new BlockItem(SPAWNER_FRAME_BLOCK.get(), new Item.Properties()));

    public static final DeferredHolder<Block, SunBlock> SUN_BLOCK =
            BLOCKS.register("sun", () -> new SunBlock(BlockBehaviour.Properties.of().strength(2.0F).noOcclusion()));
    public static final DeferredHolder<Item, BlockItem> SUN_BLOCK_ITEM =
            ITEMS.register("sun", () -> new BlockItem(SUN_BLOCK.get(), new Item.Properties()));

    /*******************************************************************************************************************
     * Block Entity                                                                                                    *
     *******************************************************************************************************************/

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EnderPadBlockEntity>> ENDER_PAD_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("ender_pad",
                    () -> BlockEntityType.Builder.of(EnderPadBlockEntity::new, ENDER_PAD_BLOCK.get
                            ()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SunBlockEntity>> SUN_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("sun",
                    () -> BlockEntityType.Builder.of(SunBlockEntity::new, SUN_BLOCK.get())
                            .build(null));

    /*******************************************************************************************************************
     * Container                                                                                                       *
     *******************************************************************************************************************/

    public static final DeferredHolder<MenuType<?>, MenuType<TrashBagMenu>> TRASH_BAG_CONTAINER =
            CONTAINERS.register("trash_bag",
                    () -> new MenuType<>(
                            ((windowId, inv) ->
                                    new TrashBagMenu(windowId, inv,
                                            inv.player.getItemInHand(InteractionHand.MAIN_HAND).getCapability(Capabilities.ItemHandler.ITEM),
                                            inv.player.getItemInHand(InteractionHand.MAIN_HAND))), FeatureFlags.DEFAULT_FLAGS));

    /*******************************************************************************************************************
     * Entity                                                                                                          *
     *******************************************************************************************************************/

//    public static final RegistryObject<EntityType<NetEntity>> NET_ENTITY =
//            ENTITIES.register("net", () ->
//                    EntityType.Builder.<NetEntity>of(NetEntity::new, MobCategory.MISC)
//                            .sized(0.5F, 0.5F)
//                            .clientTrackingRange(10)
//                            .setShouldReceiveVelocityUpdates(true)
//                            .build("net"));

    /*******************************************************************************************************************
     * Creative Tabs                                                                                                   *
     *******************************************************************************************************************/

    // Main
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB
            = CREATIVE_MODE_TABS.register(Reference.MOD_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Reference.MOD_ID))
            .icon(() -> ENDER_PAD_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ENDER_PAD_BLOCK_ITEM.get());
                output.accept(SUN_BLOCK_ITEM.get());
                output.accept(PLAYER_PLATE_BLOCK_ITEM.get());
                output.accept(SPAWNER_FRAME_BLOCK_ITEM.get());
                output.accept(CLIMBING_GLOVES_ITEM.get());
                output.accept(MAGNET_ITEM.get());
                output.accept(ELECTRIC_MAGNET_ITEM.get());
                output.accept(TRASH_BAG_ITEM.get());
                output.accept(HEFTY_BAG_ITEM.get());
                output.accept(SPAWNER_RELOCATOR_ITEM.get());
            }).build());

    // Pipettes
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PIPETTE_TAB
            = CREATIVE_MODE_TABS.register(Reference.MOD_ID + "_pipettes", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Reference.MOD_ID + "_pipettes"))
            .icon(() -> PIPETTE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(PIPETTE_ITEM.get());
            }).build());

    /*******************************************************************************************************************
     * Register Capabilities                                                                                           *
     *******************************************************************************************************************/

    @SubscribeEvent
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Magnet
        event.registerItem(
                Capabilities.EnergyStorage.ITEM,
                (stack, ctx) -> new EnergyContainingItem(stack, ElectricMagnetItem.ENERGY_CAPACITY),
                ELECTRIC_MAGNET_ITEM.get());

        // Trash Bags
        // Normal
        event.registerItem(
                Capabilities.ItemHandler.ITEM,
                (stack, ctx) -> new InventoryHandlerItem(stack, stack.getOrCreateTag()) {
                    @Override
                    protected int getInventorySize() {
                        return 1;
                    }

                    @Override
                    protected boolean isItemValidForSlot(int i, ItemStack itemStack) {
                        return true;
                    }

                    @Override
                    public boolean isItemValid(int i, @NotNull ItemStack itemStack) {
                        return true;
                    }
                },
                TRASH_BAG_ITEM.get()
        );

        // Hefty Bag
        event.registerItem(
                Capabilities.ItemHandler.ITEM,
                (stack, ctx) -> new InventoryHandlerItem(stack, stack.getOrCreateTag()) {
                    @Override
                    protected int getInventorySize() {
                        return 18;
                    }

                    @Override
                    protected boolean isItemValidForSlot(int i, ItemStack itemStack) {
                        return true;
                    }

                    @Override
                    public boolean isItemValid(int i, @NotNull ItemStack itemStack) {
                        return true;
                    }
                },
                HEFTY_BAG_ITEM.get()
        );

        // Pipette
        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, ctx) -> new FluidHandlerItemStack(stack, FluidType.BUCKET_VOLUME),
                PIPETTE_ITEM.get()
        );
    }

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        // Add to ingredients tab
        if (event.getTabKey() == PIPETTE_TAB.getKey()) {
            // Add all Fluids
            BuiltInRegistries.FLUID.stream()
                    .filter(fluid -> fluid.isSource(fluid.defaultFluidState()))
                    .forEach(fluid -> {
                        var pipetteStack = new ItemStack(PIPETTE_ITEM.get());
                        var fluidStack = new FluidHandlerItemStack(pipetteStack, FluidType.BUCKET_VOLUME);
                        if (fluidStack.fill(new FluidStack(fluid, FluidType.BUCKET_VOLUME), IFluidHandler.FluidAction.EXECUTE)
                                == FluidType.BUCKET_VOLUME)
                            event.accept(pipetteStack);
                    });
        }
    }
}