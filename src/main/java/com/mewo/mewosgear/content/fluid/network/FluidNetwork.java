package com.mewo.mewosgear.content.fluid.network;

import com.mewo.mewosgear.util.nodenetwork.INodeNetwork;
import com.mewo.mewosgear.util.nodenetwork.NodeNetworkEnums.*;
import net.minecraft.core.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.*;


public class FluidNetwork implements INodeNetwork {
    private Map<BlockPos, Node> nodes = new HashMap<>();
    private int networkId;

    public FluidNetwork(int networkId) {
        this.networkId = networkId;
    }


    // Methods

    @Override
    public void addNode(BlockPos pos, Node node) {
        nodes.put(pos, node);
    }

    @Override
    public void removeNode(Node node) {
        nodes.remove(getPosition(node));
    }

    @Override
    public boolean hasNodeAtPos(BlockPos pos) {
        return false;
    }




    @Override
    public Node getNodeFromPos(BlockPos pos) {
        return nodes.get(pos);
    }

    @Override
    public BlockPos getPosition(Node node) {
        for (Map.Entry<BlockPos, Node> entry : nodes.entrySet()) {
            if (entry.getValue().id() == node.id()) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public Node getNode(int id) {
        for (Map.Entry<BlockPos, Node> entry : nodes.entrySet()) {
            if (entry.getValue().id() == id) return entry.getValue();
        }
        return null;
    }

    @Override
    public int getNetworkId() {
        return networkId;
    }

    @Override
    public NodeNetworkType getNetworkType() {
        return NodeNetworkType.FLUID;
    }

    @Override
    public int getNodeCount() {
        return nodes.size();
    }

    @Override
    public Map<BlockPos, Node> getNodes() {
        return Map.copyOf(nodes);
    }







//    private List<Integer> getIdPathFromPrevious(Node start, Node end, Map<BlockPos, BlockPos> previous) {
//        List<Integer> path = new ArrayList<>();
//        BlockPos endPos = getPosition(end);
//        for (BlockPos current = endPos; current != null; current = previous.get(current)) {
//            path.add(getNodes().get(current).id());
//        }
//
//        Collections.reverse(path);
//        return path;
//    }
//
//    @Override
//    public List<Integer> getPath(Node start, Node end) {
//        BlockPos startPos = getPosition(start);
//
//        Queue<BlockPos> queue = new ArrayDeque<>();
//        queue.add(startPos);
//
//        Set<BlockPos> visited = new HashSet<>();
//        visited.add(startPos);
//
//        Map<BlockPos, BlockPos> previous = new HashMap<>();
//        boolean connected = false;
//
//        while (!queue.isEmpty()) {
//            BlockPos currentPos = queue.poll();
//            Node currentNode = getNodes().get(currentPos);
//
//            if (currentNode.id() == end.id()) {
//                connected = true;
//                queue.clear();
//                break;
//            }
//
//            int[] neighborIds = getNeighbors(currentNode);
//
//            for (int neighborId : neighborIds) {
//                Node neighborNode = getNode(neighborId);
//                BlockPos neighborPos = getPosition(neighborNode);
//                if (visited.add(neighborPos)) {
//                    queue.add(neighborPos);
//                    previous.put(neighborPos, currentPos);
//                }
//            }
//        }
//        return getIdPathFromPrevious(start, end, previous);
//    }
}
