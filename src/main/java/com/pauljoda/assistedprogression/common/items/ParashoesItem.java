package com.pauljoda.assistedprogression.common.items;

import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 6/6/2022
 */
public class ParashoesItem extends ArmorItem implements IAdvancedToolTipProvider {
    public static enum ParashoesMaterial implements ArmorMaterial {
        PARASHOES_MATERIAL;

        @Override
        public int getDurabilityForSlot(EquipmentSlot slot) {
            return 125;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot p_40411_) {
            return 1;
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(Tags.Items.FEATHERS);
        }

        @Override
        public String getName() {
            return Reference.MOD_ID + ":" + "parashoes";
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    }

    public ParashoesItem() {
        super(ParashoesMaterial.PARASHOES_MATERIAL,
                EquipmentSlot.FEET,
                new Properties()
                        .stacksTo(1)
                        .tab(Registration.tabAssistedProgression));
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment != Enchantments.FALL_PROTECTION || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    /*******************************************************************************************************************
     * IAdvancedToolTipProvider                                                                                        *
     *******************************************************************************************************************/

    @Nullable
    @Override
    public List<String> getAdvancedToolTip(@NotNull ItemStack itemStack) {
        return List.of(ClientUtils.translate("parashoes.desc"));
    }
}
