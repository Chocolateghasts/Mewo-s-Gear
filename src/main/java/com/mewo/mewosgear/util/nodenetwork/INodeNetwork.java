package com.mewo.mewosgear.util.nodenetwork;

import com.mewo.mewosgear.util.nodenetwork.NodeNetworkEnums.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

import java.util.*;

public interface INodeNetwork {

    int getNetworkId();
    NodeNetworkType getNetworkType();

    int getNodeCount();
    Map<BlockPos, Node> getNodes();

    void addNode(BlockPos pos, Node node);
    void removeNode(Node node);

    boolean hasNodeAtPos(BlockPos pos);
    Node getNodeFromPos(BlockPos pos);
    BlockPos getPosition(Node node);
    Node getNode(int id);

    default int[] getNeighbors(Node node) {
        int[] nodes = new int[6];

        BlockPos pos = getPosition(node);
        for (Direction direction : Direction.values()) {
            Vec3i offset = new Vec3i(
                    direction.getStepX(),
                    direction.getStepY(),
                    direction.getStepZ());
            BlockPos newPos = pos.offset(offset);
            nodes[direction.ordinal()] = getNodeFromPos(newPos).id();
        }

        return nodes;
    }

    default List<Integer> getIdPathFromPrevious(Node start, Node end, Map<BlockPos, BlockPos> previous) {
        List<Integer> path = new ArrayList<>();
        BlockPos endPos = getPosition(end);
        for (BlockPos current = endPos; current != null; current = previous.get(current)) {
            path.add(getNodes().get(current).id());
        }

        Collections.reverse(path);
        return path;
    }

    default List<Integer> getPath(Node start, Node end) {
        BlockPos startPos = getPosition(start);

        Queue<BlockPos> queue = new ArrayDeque<>();
        queue.add(startPos);

        Set<BlockPos> visited = new HashSet<>();
        visited.add(startPos);

        Map<BlockPos, BlockPos> previous = new HashMap<>();
        boolean connected = false;

        while (!queue.isEmpty()) {
            BlockPos currentPos = queue.poll();
            Node currentNode = getNodes().get(currentPos);

            if (currentNode.id() == end.id()) {
                connected = true;
                queue.clear();
                break;
            }

            int[] neighborIds = getNeighbors(currentNode);

            for (int neighborId : neighborIds) {
                Node neighborNode = getNode(neighborId);
                BlockPos neighborPos = getPosition(neighborNode);
                if (visited.add(neighborPos)) {
                    queue.add(neighborPos);
                    previous.put(neighborPos, currentPos);
                }
            }
        }
        return getIdPathFromPrevious(start, end, previous);
    }


}
