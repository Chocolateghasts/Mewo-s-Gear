package com.mewo.mewosgear.util.nodenetwork;

import net.minecraft.core.BlockPos;

public class NodeNetworkEnums {
    public record Node(int id, NodeType nodeType, int weight) {}

    public enum NodeNetworkType {
        ENERGY, FLUID, CUSTOM
    }

    public enum NodeType {
        CONSUMER, PRODUCER, MEDIUM, STORAGE, CUSTOM, NEUTRAL, BLOCK
    }
}
