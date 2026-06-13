package com.mewo.mewosgear.content.block.functional.chemical_factory;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraftforge.common.capabilities.ForgeCapabilities.*;

public class BlockEntityChemicalFactory extends BlockEntity implements MenuProvider {

    // Constants
    public static final int SLOT_COUNT = 3;
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    // Capabilities
    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT);
    LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

//    private FluidHandler

    // Data
    protected ContainerData containerData;

    public BlockEntityChemicalFactory(BlockPos pos, BlockState blockState) {
        super(, pos, blockState);
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
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }

    // Misc
    public void drops() {
        SimpleContainer container = new SimpleContainer(SLOT_COUNT);
        for (int i = 0; i < SLOT_COUNT; i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
    }

    // Capability stuff


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        if (cap == ITEM_HANDLER) return lazyItemHandler.cast();
//        if (cap == FLUID_HANDLER) return ;

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);

        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }
}

