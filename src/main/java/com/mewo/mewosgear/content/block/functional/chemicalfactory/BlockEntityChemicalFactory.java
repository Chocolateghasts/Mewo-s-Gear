package com.mewo.mewosgear.content.block.functional.chemicalfactory;

import com.mewo.mewosgear.content.fluid.MultiFluidTankHandler;
import com.mewo.mewosgear.content.fluid.MultiFluidTankHandler.TankEntry;
import com.mewo.mewosgear.content.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.mewo.mewosgear.content.fluid.MultiFluidTankHandler.TankType.*;
import static net.minecraftforge.common.capabilities.ForgeCapabilities.*;

public class BlockEntityChemicalFactory extends BlockEntity implements MenuProvider {

    // Constants
    public static final int SLOT_COUNT = 4;
    public static final int INPUT_SLOT_1 = 0;
    public static final int INPUT_SLOT_2 = 1;
    public static final int OUTPUT_SLOT_1 = 2;
    public static final int OUTPUT_SLOT_2 = 3;
    public static final int FLUID_CAPACITY = 4000;
    public static final int ENERGY_CAPACITY = 16000;
    public static final int ENERGY_RECEIVE = 2000;
    public static final int ENERGY_EXTRACT = 2000;

    // Capabilities

        // Item
    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT);
    LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

        // Fluid
    private final FluidTank inputTank = new FluidTank(FLUID_CAPACITY);
    private final FluidTank inputTank1 = new FluidTank(FLUID_CAPACITY);
    private final FluidTank outputTank = new FluidTank(FLUID_CAPACITY);
    private final FluidTank outputTank1 = new FluidTank(FLUID_CAPACITY);
    private MultiFluidTankHandler fluidTankHandler = new MultiFluidTankHandler(new TankEntry[]{
            new TankEntry(inputTank, INPUT),
            new TankEntry(inputTank1, INPUT),
            new TankEntry(outputTank, OUTPUT),
            new TankEntry(outputTank1, OUTPUT)
    });
    LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

        // Electricity
    private final EnergyStorage energyStorage = new EnergyStorage(ENERGY_CAPACITY, ENERGY_RECEIVE, ENERGY_EXTRACT);
    LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    // Data
    protected ContainerData containerData;

    public BlockEntityChemicalFactory(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CHEMICAL_FACTORY_BE.get(), pos, blockState);
        this.containerData = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return 0;
            }

            @Override
            public void set(int pIndex, int pValue) {

            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    // Function
    public void tick(Level level, BlockPos pos, BlockState state) {

    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.mewosgear.chemical_factory");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        System.out.println("created menu");
        return new MenuChemicalFactory(containerId, inventory, this, this.containerData);
    }

    // Misc

    // TODO: add mix fluid drop on break, make it not destroy items to preserve the machine
    public void drops() {
        SimpleContainer container = new SimpleContainer(SLOT_COUNT);
        for (int i = 0; i < SLOT_COUNT; i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    // Capability stuff


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        if (cap == ITEM_HANDLER) return lazyItemHandler.cast();
        else if (cap == FLUID_HANDLER) return lazyFluidHandler.cast();
        else if (cap == ENERGY) return lazyEnergyHandler.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> fluidTankHandler);

    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        fluidTankHandler.serializeNBT(nbt);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        fluidTankHandler.deserializeNBT(nbt);
    }
}

