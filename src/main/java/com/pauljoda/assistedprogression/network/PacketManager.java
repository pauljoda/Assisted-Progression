package com.pauljoda.assistedprogression.network;

import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.assistedprogression.network.packets.BoneMealParticlesPacket;
import com.pauljoda.assistedprogression.network.packets.NotifyServerOfTrashBagChanges;
import com.pauljoda.nucleus.network.packet.INetworkMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
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
public class PacketManager {
    // Our network wrapper
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Reference.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    /**
     * Registers all packets
     */
    public static void initPackets() {
        registerMessage(NotifyServerOfTrashBagChanges.class, NotifyServerOfTrashBagChanges::process);
        registerMessage(BoneMealParticlesPacket.class, BoneMealParticlesPacket::process);
    }

    // Local hold for next packet id
    private static int nextPacketId = 0;

    /**
     * Registers a message to the network registry
     * @param packet The packet class
     */
    @SuppressWarnings("unchecked")
    public static <T extends INetworkMessage> void registerMessage(Class<T> packet,
                                                                   BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        INSTANCE.registerMessage(nextPacketId, packet,
                INetworkMessage::encode,
                (buf) -> {
                    try {
                        T msg = packet.newInstance();
                        msg.decode(buf);
                        return msg;
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                },
                messageConsumer);
        nextPacketId++;
    }
}
