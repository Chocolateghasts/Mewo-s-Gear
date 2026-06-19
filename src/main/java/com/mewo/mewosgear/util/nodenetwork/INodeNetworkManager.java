package com.mewo.mewosgear.util.nodenetwork;

import net.minecraft.core.BlockPos;

import java.util.Set;

public interface INodeNetworkManager {

    boolean addNetwork(int networkId);
    boolean removeNetwork(int networkId);

    Set<Integer> getNetworks();
    void split(BlockPos splitPos, int networkId);
}
