package com.mewo.mewosgear.content.block.functional.tool_modification_table;

import com.mewo.mewosgear.content.item.SpecialSwordItem;
import com.mewo.mewosgear.content.modifiers.IModifier;
import com.mewo.mewosgear.content.modifiers.ModifierItem;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraftforge.common.capabilities.ForgeCapabilities.*;

public class BlockEntityToolModificationTable extends BlockEntity implements MenuProvider {
    // Constants
    public static final int SLOT_COUNT = 3;
    public static final int TOOL_SLOT = 0;
    public static final int MODIFIER_SLOT = 1;
    public static final int MISC_SLOT = 2;

    // Capabilities
    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT);
    LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    // Data
    protected ContainerData containerData;


    public BlockEntityToolModificationTable(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TOOL_MODIFICATION_TABLE_BE.get(), pos, blockState);
        this.containerData = new ContainerData() {

            @Override
            public int get(int i) {
                return 0;
            }

            @Override
            public void set(int i, int i1) {

            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }


    // Function

    private boolean hasTool;
    private boolean hasModifier;
    private boolean hasMisc;

    public void tick(Level level, BlockPos pos, BlockState state) {
        ItemStack tool = itemHandler.getStackInSlot(0);
        ItemStack modifier = itemHandler.getStackInSlot(1);
        hasTool = (!tool.isEmpty() && tool.getItem() instanceof SpecialSwordItem);

        hasModifier = (!modifier.isEmpty() && modifier.getItem() instanceof ModifierItem);

//        System.out.println("HasTool: " + hasTool);
//        System.out.println("HasModifier: " + hasModifier);


    }

    public void tryApplyModifier() {
        if (hasTool && hasModifier) {
            ItemStack tool = itemHandler.getStackInSlot(0);
            ItemStack modifier = itemHandler.getStackInSlot(1);
            SpecialSwordItem specialSwordItem = (SpecialSwordItem) tool.getItem();
            IModifier iModifier = ((ModifierItem) modifier.getItem()).getCurrentModifier();

            if (specialSwordItem.addModifier(iModifier, tool)) modifier.shrink(1);
        }
    }

    // Info
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.mewosgear.tool_modification_table");
    }

    // GUI
    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MenuToolModificationTable(i, inventory, this, this.containerData);
    }

    // MISC
    public void drops() {
        SimpleContainer container = new SimpleContainer(SLOT_COUNT);
        for (int i = 0; i < SLOT_COUNT; i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    /* Capability Stuff */

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ITEM_HANDLER) return lazyItemHandler.cast();

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

    // Save/Load

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        compoundTag.put("inventory", itemHandler.serializeNBT());

        super.saveAdditional(compoundTag);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);

        itemHandler.deserializeNBT(compoundTag.getCompound("inventory"));
    }
}
