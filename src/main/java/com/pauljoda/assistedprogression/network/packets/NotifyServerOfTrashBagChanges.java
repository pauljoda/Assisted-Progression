package com.pauljoda.assistedprogression.network.packets;

import com.pauljoda.assistedprogression.common.items.container.TrashBagContainer;
import com.pauljoda.nucleus.network.packet.INetworkMessage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 6/8/2022
 */
public class NotifyServerOfTrashBagChanges implements INetworkMessage {

    public CompoundTag tag;

    public NotifyServerOfTrashBagChanges() {}

    public NotifyServerOfTrashBagChanges(CompoundTag compoundTag) {
        this.tag = compoundTag;
    }

    @Override
    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeNbt(tag);
    }

    @Override
    public void decode(FriendlyByteBuf friendlyByteBuf) {
        tag = friendlyByteBuf.readNbt();
    }

    /*******************************************************************************************************************
     * IMessageHandler                                                                                                 *
     *******************************************************************************************************************/

    public static void process(NotifyServerOfTrashBagChanges message, Supplier<NetworkEvent.Context> ctx) {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            var player = ctx.get().getSender();
            if(player.containerMenu instanceof TrashBagContainer trashBagContainer) {
                trashBagContainer.trashBag.setTag(message.tag);
                trashBagContainer.broadcastChanges();
            }
            ctx.get().setPacketHandled(true);
        }
    }
}
