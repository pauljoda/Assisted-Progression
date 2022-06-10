package com.pauljoda.assistedprogression.data;

import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

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
public class TranslationGenerator extends LanguageProvider {

    public TranslationGenerator(DataGenerator gen, String locale) {
        super(gen, Reference.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        // Creative Tab
        add("itemGroup." + Reference.MOD_ID, "Assisted Progression");
        add("itemGroup." + Reference.MOD_ID + "_pipettes", "Assisted Progression : Pipettes");

        // Items -------------------------------------------------------------------------------------------------------
        // Spawner Relocator
        addWithDescription(Registration.SPAWNER_RELOCATOR_ITEM
                , "Spawner Relocator",
                "Hold right click and release while looking at a spawner to pick it up, hold right click while aiming at a block to place it back down");
        add("assisted_progression.text.spawnerRelocator.type", "%s%sType: %s");

        // Magnets
        addWithDescription(Registration.MAGNET_ITEM,
                "Magnet",
                "Attracts all sorts of things, some dangerous. Shift+Right-Click to toggle activation");
        addWithDescription(Registration.ELECTRIC_MAGNET_ITEM,
                "Electric Magnet",
                "Attracts item entities and xp orbs, requires power. Shift+Right-Click to toggle activation");

        // Pipette
        addWithDescription(Registration.PIPETTE_ITEM,
                "Pipette",
                "Holds up to one bucket of fluid, useful for removing fluid amounts less than one bucket. Right click fluid handling tiles to insert and extract fluid");
        add("assisted_progression.text.pipette.fluidStored", "Fluid Contained:");
        add("assisted_progression.text.pipette.empty", "Empty");

        // Parashoes
        add(Registration.PARASHOES_ITEM.get(), "Parashoes");
        add("parashoes.desc", "Wear these shoes to slow your decent");

        // Climbing Gloves
        addWithDescription(Registration.CLIMBING_GLOVES_ITEM,
                "Climbing Gloves",
                "Have in your inventory to scale walls by walking into them");

        // Trash Bags
        addWithDescription(Registration.TRASH_BAG_ITEM,
                "Trash Bag",
                "Contains one slot, will void items matching filter on pickup");
        add("trash_bag", "Trash Bag");
        addWithDescription(Registration.HEFTY_BAG_ITEM,
                "Hefty Bag",
                "Contains 18 slots, will void items matching filter on pickup");
        add("hefty_bag", "Hefty Bag");

        // Nets
        addWithDescription(Registration.NET_ITEM,
                "Mob Net",
                "Use with Mob Net Launcher to launch nets that capture mobs, right click to place captured mobs");
        add("assisted_progression.text.net.stored", "Entity Stored: ");

        // Net Launcher
        addWithDescription(Registration.NET_LAUNCHER_ITEM,
                "Mob Net Launcher",
                "Launches mob nets from inventory to capture mobs");

        // Blocks ------------------------------------------------------------------------------------------------------
        // Ender Pad
        add(Registration.ENDER_PAD_BLOCK.get(), "Ender Pad");
        add("ender_pad.desc", "Place another in the path of the arrow on top and sneak while standing on one to teleport to the other");

        // Player Plate
        add(Registration.PLAYER_PLATE_BLOCK.get(), "Player Sensitive Pressure Plate");
        add("player_player.desc", "Pressure plate that will only activate for player entities");

        // Spawner Frame
        add(Registration.SPAWNER_FRAME_BLOCK.get(), "Spawner Frame");
        add("spawner_frame.desc", "Right click with a mob net with a captured mob to create a spawner of that type");

        // Entities ----------------------------------------------------------------------------------------------------

    }

    private void addWithDescription(RegistryObject<Item> item, String name, String desc) {
        add(item.get(), name);
        add(item.get().getRegistryName() + ".desc", desc);
    }
}
