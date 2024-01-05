package com.pauljoda.assistedprogression.common.items;

import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * BaseItem class.
 * <p>
 * This class is a base item class which contains three constructors for variations of item stacks.
 * Implements {@code IAdvancedToolTipProvider} interface to provide advanced tooltips to items.
 * This class is part of the com.pauljoda.assistedprogression.common.items package.
 *
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 6/10/2022
 */
public class BaseItem extends Item implements IAdvancedToolTipProvider {

    /**
     * Default constructor which creates a BaseItem with a stack size of 1.
     */
    public BaseItem() {
        this(new Properties()
                .stacksTo(1));
    }

    /**
     * Constructor which creates a BaseItem with a stack size defined by the user.
     *
     * @param stackSize The stack size of the item.
     */
    public BaseItem(int stackSize) {
        this(new Properties()
                .stacksTo(stackSize));
    }

    /**
     * Constructor that creates a BaseItem using Properties defined by the user.
     *
     * @param props The item properties.
     */
    public BaseItem(Properties props) {
        super(props);
    }

    /*******************************************************************************************************************
     * IAdvancedToolTipProvider                                                                                        *
     *******************************************************************************************************************/

    /**
     * Returns the advanced tooltip of the item.
     *
     * @param itemStack The item stack for which the tooltip is to be returned.
     * @return A list containing a string that represents the item tooltip.
     */
    @Nullable
    @Override
    public List<String> getAdvancedToolTip(@NotNull ItemStack itemStack) {
        return List.of(ChatFormatting.GREEN + ClientUtils.translate(this.getDescriptionId() + ".desc"));
    }
}