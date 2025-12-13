package com.mewo.mewosgear.content.block.crafting.tool_modification_table;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class MenuToolModificationTable extends AbstractContainerMenu {
    public final BlockEntityToolModificationTable blockEntityToolModificationTable;
    private final Level level;
    private final ContainerData containerData;

    private final int containerSize = 2;

    public MenuToolModificationTable(int containerId, Inventory inventory, FriendlyByteBuf data) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(containerId));
    }

    public MenuToolModificationTable(int containerId, Inventory inventory, BlockEntity blockEntity, ContainerData data) {
        super(null, containerId); // TODO: fix null
        checkContainerSize(inventory, containerId);
        blockEntityToolModificationTable = (BlockEntityToolModificationTable) blockEntity;
        this.level = inventory.player.level();
        this.containerData = data;

        addPlayerInventory(inventory);
        addPlayerHotBar(inventory);

        this.blockEntityToolModificationTable.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
                this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 11));
                this.addSlot(new SlotItemHandler(iItemHandler, 1, 80, 59));
    });

        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotBar(Inventory inventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }
}
