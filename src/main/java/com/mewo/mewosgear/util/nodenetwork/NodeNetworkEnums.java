package com.mewo.mewosgear.util.nodenetwork;

import net.minecraft.core.BlockPos;

public class NodeNetworkEnums {
    public record Node(int id) {}

    public enum NodeNetworkType {
        ENERGY, FLUID, CUSTOM
    }
}
