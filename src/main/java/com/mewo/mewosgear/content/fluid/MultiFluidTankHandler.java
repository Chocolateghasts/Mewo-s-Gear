package com.mewo.mewosgear.content.fluid;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MultiFluidTankHandler implements IFluidHandler {
    public enum TankType { INPUT, OUTPUT }
    public record TankEntry(FluidTank tank, TankType type) {}

    private final List<TankEntry> tanks = new ArrayList<>();

    public MultiFluidTankHandler(TankEntry[] tanks) {
        this.tanks.addAll(Arrays.asList(tanks));
    }

    @Override
    public int getTanks() {
        return tanks.size();
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int i) {
        return tanks.get(i).tank().getFluid();
    }

    @Override
    public int getTankCapacity(int i) {
        return tanks.get(i).tank().getCapacity();
    }

    @Override
    public boolean isFluidValid(int i, @NotNull FluidStack fluidStack) {
        return tanks.get(i).tank().isFluidValid(fluidStack);
    }

    @Override
    public int fill(FluidStack fluidStack, FluidAction fluidAction) {
        int filled = 0;
        int total = fluidStack.getAmount();

        for (TankEntry tankEntry : tanks) {
            if (tankEntry.type() == TankType.OUTPUT) continue;
            FluidTank fluidTank = tankEntry.tank();
            if (total <= 0) break;
            if (!fluidTank.isFluidValid(fluidStack)) continue;
            FluidStack toFill = new FluidStack(fluidStack, total);
            int currentFill = fluidTank.fill(toFill, fluidAction);
            filled += currentFill;
            total -= currentFill;
        }

        return filled;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
        FluidType fluidType = fluidStack.getFluid().getFluidType();
        int requestedAmount = fluidStack.getAmount();
        int remaining = requestedAmount;
        FluidStack toReturn = FluidStack.EMPTY;

        for (TankEntry tankEntry : tanks) {
            FluidTank fluidTank = tankEntry.tank();
            if (remaining <= 0) break;
            if (fluidTank.getFluid().getFluid().getFluidType() != fluidType) continue;

            FluidStack drained = fluidTank.drain(remaining, fluidAction);
            if (toReturn.isEmpty()) toReturn = drained.copy();
            else toReturn.grow(drained.getAmount());
            remaining = requestedAmount - toReturn.getAmount();
        }

        return toReturn;
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction fluidAction) {
        int remaining = maxDrain;
        FluidStack toReturn = FluidStack.EMPTY;
        FluidType fluidType = null;

        for (TankEntry tankEntry : tanks) {
            FluidTank fluidTank = tankEntry.tank();
            if (remaining <= 0) break;
            FluidType tankFluidType = fluidTank.getFluid().getFluid().getFluidType();
            if (fluidType == null) fluidType = tankFluidType;
            else if (fluidType != tankFluidType) continue;

            FluidStack drained = fluidTank.drain(remaining, fluidAction);
            if (drained.isEmpty()) continue;
            if (toReturn.isEmpty()) toReturn = drained.copy();
            else toReturn.grow(drained.getAmount());
            remaining = maxDrain - toReturn.getAmount();
        }

        return toReturn;
    }

    public void serializeNBT(CompoundTag compoundTag) {
        for (int i = 0; i < tanks.size(); i++) {
            TankEntry tankEntry = tanks.get(i);
            compoundTag.put(tankEntry.type() + "_" + i, tankEntry.tank().writeToNBT(new CompoundTag()));
        }
    }

    public void deserializeNBT(CompoundTag compoundTag) {
        for (int i = 0; i < tanks.size(); i++) {
            TankEntry tankEntry = tanks.get(i);
            tankEntry.tank().readFromNBT(compoundTag.getCompound(tankEntry.type() + "_" + i));
        }
    }
}
