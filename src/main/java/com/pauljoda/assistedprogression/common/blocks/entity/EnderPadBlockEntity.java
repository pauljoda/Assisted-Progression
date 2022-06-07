package com.pauljoda.assistedprogression.common.blocks.entity;

import com.pauljoda.assistedprogression.common.blocks.EnderPadBlock;
import com.pauljoda.assistedprogression.lib.Registration;
import com.pauljoda.nucleus.common.blocks.entity.Syncable;
import com.pauljoda.nucleus.common.blocks.entity.UpdatingBlockEntity;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 6/7/2022
 */
public class EnderPadBlockEntity extends Syncable {

    protected static final int TELEPORT_RANGE = 20;

    // Used to play sound
    protected static final int PLAY_SOUND = 0;

    // Cool down before we can move again
    protected int coolDown = 0;

    // AABB to be created
    protected AABB blockAbove;

    public EnderPadBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.ENDER_PAD_BLOCK_ENTITY.get(), pos, state);
        blockAbove = new AABB(pos.getX(), pos.getY() + 1, pos.getZ(),
                pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onClientTick() {
        if (coolDown > 0) {
            coolDown--;
        } else if (hasLevel()) {
            // See if there is a player above and shifting
            var entitiesAbove = level.getEntitiesOfClass(Player.class, blockAbove);
            // Move Players
            if (!entitiesAbove.isEmpty()) {
                for (Player player : entitiesAbove) {
                    // Only if sneaking
                    if (!player.isShiftKeyDown()) continue;

                    var direction = level.getBlockState(getBlockPos()).getValue(EnderPadBlock.FACING);
                    // Try to find other block
                    for (int i = 1; i <= TELEPORT_RANGE; i++) {
                        var entityInPosition = level.getBlockEntity(getBlockPos().relative(direction, i));
                        if (entityInPosition instanceof EnderPadBlockEntity otherPad) {
                            this.coolDown = 10;
                            otherPad.coolDown = 10;

                            // Move Player
                            var newPosition = otherPad.getBlockPos().relative(Direction.UP);
                            player.setPos(
                                    new Vec3(newPosition.getX() + 0.5D, newPosition.getY(), newPosition.getZ() + 0.5D));
                            otherPad.sendValueToServer(PLAY_SOUND, 0);
                            var particlesToSpawn = 5;
                            for(int j = 0; j < particlesToSpawn; j++)
                                level.addParticle(ParticleTypes.PORTAL,
                                        otherPad.getBlockPos().getX() + 0.5D,
                                        otherPad.getBlockPos().getY() + 1.5D,
                                        otherPad.getBlockPos().getZ() + 0.5D,
                                        -1 + level.random.nextDouble(2),
                                        -1 + level.random.nextDouble(2),
                                        -1 + level.random.nextDouble(2));

                            return;
                        }
                    }
                }
            }
        }
    }

    /*******************************************************************************************************************
     * Syncable                                                                                                        *
     *******************************************************************************************************************/

    @Override
    public void setVariable(int variable, double v) {
        switch (variable) {
            case PLAY_SOUND:
                if (hasLevel()) {
                    playSound(level, getBlockPos().above(1), SoundEvents.ENDERMAN_TELEPORT);
                   }
                break;
            default:
        }
    }

    @Override
    public Double getVariable(int i) {
        return 0.0;
    }

    /**
     * Plays a sound
     *
     * @param level      Level to play in
     * @param blockPos   Position of sound (will shift to middle of block)
     * @param soundEvent Sound to play
     */
    static void playSound(Level level, BlockPos blockPos, SoundEvent soundEvent) {
        double d0 = (double) blockPos.getX() + 0.5D;
        double d1 = (double) blockPos.getY() + 0.5D;
        double d2 = (double) blockPos.getZ() + 0.5D;

        level.playSound((Player) null, d0, d1, d2, soundEvent, SoundSource.BLOCKS, 0.5F,
                level.random.nextFloat() * 0.1F + 0.9F);
    }
}
