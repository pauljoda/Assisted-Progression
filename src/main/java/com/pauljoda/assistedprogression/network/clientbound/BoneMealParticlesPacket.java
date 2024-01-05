package com.pauljoda.assistedprogression.network.clientbound;

import com.pauljoda.nucleus.network.packets.ClientBoundPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;

public record BoneMealParticlesPacket(BlockPos pos) implements ClientBoundPacket {

    /*******************************************************************************************************************
     * Encode/Decode                                                                                                   *
     *******************************************************************************************************************/

    public static BoneMealParticlesPacket decode(FriendlyByteBuf buf) {
        var position = buf.readBlockPos();
        return new BoneMealParticlesPacket(position);
    }

    /**
     * @param friendlyByteBuf
     */
    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(pos);
    }

    /**
     * @param player
     */
    @Override
    public void handleOnClient(Player player) {
        if(pos != null )
            BoneMealItem.addGrowthParticles(player.level(), pos, 15);
    }
}
