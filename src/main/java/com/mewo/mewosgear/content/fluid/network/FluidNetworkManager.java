package com.mewo.mewosgear.content.fluid.network;

import com.mewo.mewosgear.util.nodenetwork.INodeNetworkManager;
import net.minecraft.core.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class FluidNetworkManager implements INodeNetworkManager {
    private static Set<Integer> networks = new HashSet<>();

    @Override
    public boolean addNetwork(int networkId) {
        return false;
    }

    @Override
    public boolean removeNetwork(int networkId) {
        return false;
    }

    @Override
    public Set<Integer> getNetworks() {
        return networks;
    }

    @Override
    public void split(BlockPos splitPos, int networkId) {

    }
}
