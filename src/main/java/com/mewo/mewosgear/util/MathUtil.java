package com.mewo.mewosgear.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MathUtil {
    // TODO: Improve limit handling, add compat for high values, improve algorithm
    record PosDepth(BlockPos pos, int depth) {}

    private static final int DEFAULT_DEPTH = 200;
    private static final int MAX_REASONABLE_DEPTH = 500;

    private static List<BlockPos> getAdjacentBlocks(BlockPos origin) {
        List<BlockPos> foundBlocks = new ArrayList<>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) continue;

                    BlockPos pos = origin.offset(dx, dy, dz);
                    foundBlocks.add(pos);
                }
            }
        }

        return foundBlocks;
    }

    /**
     * @param depth -1 for infinite, 0 for default limit
     *              Please do not use infinite or high values it WILL crash.
     */
    public static Set<BlockPos> getAllAdjacentBlocks(BlockPos startPos, int depth, Level level, BlockState blockState) {

        Block blockType = blockState.getBlock();
        Set<BlockPos> visited = new HashSet<>();
        Queue<PosDepth> targets = new ArrayDeque<>();

        if (depth == -1) { depth = Integer.MAX_VALUE; }
        else if (depth == 0) { depth = DEFAULT_DEPTH; }

        targets.add(new PosDepth(startPos, 0));
        visited.add(startPos);

        while (!targets.isEmpty()) {

            PosDepth posDepth = targets.poll();
            BlockPos currentPos = posDepth.pos;

            if (posDepth.depth >= depth) continue;

            List<BlockPos> neighbors = getAdjacentBlocks(currentPos);
            for (BlockPos pos : neighbors) {

                Block foundBlock = level.getBlockState(pos).getBlock();
                if (foundBlock == blockType && visited.add(pos)) {
                    targets.add(new PosDepth(pos, posDepth.depth + 1));
                }
            }
        }
        return visited;
    }
}
