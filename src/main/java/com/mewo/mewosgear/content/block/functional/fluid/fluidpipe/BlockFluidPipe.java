package com.mewo.mewosgear.content.block.functional.fluid.fluidpipe;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlockFluidPipe extends BaseEntityBlock {
    private static final VoxelShape VOXEL_SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    protected BlockFluidPipe(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }
}
