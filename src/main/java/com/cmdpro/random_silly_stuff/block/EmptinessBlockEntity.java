package com.cmdpro.random_silly_stuff.block;

import com.cmdpro.random_silly_stuff.registries.BlockEntityRegistry;
import com.cmdpro.random_silly_stuff.registries.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class EmptinessBlockEntity extends BlockEntity {
    public EmptinessBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.EMPTINESS.get(), pos, state);
    }
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, EmptinessBlockEntity pBlockEntity) {
        if (pLevel.isClientSide) {
            for (int i = 0; i < 5; i++) {
                Vec3 dir = Vec3.directionFromRotation(pLevel.random.nextIntBetweenInclusive(-360, 360), pLevel.random.nextIntBetweenInclusive(-360, 360));
                Vec3 pos = pPos.getCenter().add(dir.scale(4));
                Vec3 speed = pos.vectorTo(pPos.getCenter()).scale(1f / 10f);
                pLevel.addParticle(ParticleRegistry.EMPTINESS.get(), pos.x, pos.y, pos.z, speed.x, speed.y, speed.z);
            }
        }
    }
}
