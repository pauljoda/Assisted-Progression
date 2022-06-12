package com.pauljoda.assistedprogression.network.packets;

import com.pauljoda.nucleus.Nucleus;
import com.pauljoda.nucleus.network.packet.INetworkMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.BoneMealItem;
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
 * @since 6/12/2022
 */
public class BoneMealParticlesPacket implements INetworkMessage {

    public BlockPos pos;

    public BoneMealParticlesPacket() {}

    public BoneMealParticlesPacket(BlockPos position) {
        this.pos = position;
    }

    @Override
    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(pos);
    }

    @Override
    public void decode(FriendlyByteBuf friendlyByteBuf) {
        pos = friendlyByteBuf.readBlockPos();
    }

    /*******************************************************************************************************************
     * IMessageHandler                                                                                                 *
     *******************************************************************************************************************/

    public static void process(BoneMealParticlesPacket message, Supplier<NetworkEvent.Context> ctx) {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            var pos = message.pos;
            var level = Nucleus.proxy.getClientWorld();
            if(pos != null)
                BoneMealItem.addGrowthParticles(level, pos, 15);
            ctx.get().setPacketHandled(true);
        }
    }
}
