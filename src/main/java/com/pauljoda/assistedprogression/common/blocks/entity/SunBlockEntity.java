package com.pauljoda.assistedprogression.common.blocks.entity;

import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.assistedprogression.network.PacketManager;
import com.pauljoda.assistedprogression.network.packets.BoneMealParticlesPacket;
import com.pauljoda.nucleus.common.blocks.entity.UpdatingBlockEntity;
import com.pauljoda.nucleus.util.TimeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import java.util.Random;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 6/11/2022
 */
public class SunBlockEntity extends UpdatingBlockEntity {

    protected static Random rand = new Random();

    protected static int MAX_PER_OPERATION = 3;

    public SunBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.SUN_BLOCK_ENTITY.get(), pos, state);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onServerTick() {
        if (level != null) {
            if (rand.nextInt(20 * 20) == 0) {
                var count = 0;
                var cornerOne = getBlockPos().south(3).west(3).below(3);
                var cornerTwo = getBlockPos().north(3).east(3);
                var blocksInRange = BlockPos.betweenClosed(cornerOne, cornerTwo);

                for (BlockPos pos : blocksInRange) {
                    if (rand.nextInt(10) == 0 &&
                            !level.getBlockState(pos).isAir() &&
                            level.getBlockState(pos).getBlock() instanceof BonemealableBlock bonemealableBlock &&
                            bonemealableBlock.isValidBonemealTarget(level, pos, level.getBlockState(pos), true)) {
                        bonemealableBlock.performBonemeal((ServerLevel) level, rand, pos, level.getBlockState(pos));
                        level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 3);
                        PacketManager.INSTANCE
                                .send(PacketDistributor.NEAR.with(() ->
                                                new PacketDistributor.TargetPoint(
                                                        pos.getX(), pos.getY(), pos.getZ(), 15, level.dimension())),
                                        new BoneMealParticlesPacket(pos));
                    }
                }
            }
        }
    }
}
