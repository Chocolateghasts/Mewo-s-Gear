package com.mewo.mewosgear.content.block.functional.tool_modification_table;

import com.mewo.mewosgear.content.registry.ModBlocks;
import com.mewo.mewosgear.content.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

import static com.mewo.mewosgear.content.block.functional.tool_modification_table.BlockEntityToolModificationTable.*;

public class MenuToolModificationTable extends AbstractContainerMenu {
    public final BlockEntityToolModificationTable blockEntity;
    private final Level level;
    private final ContainerData containerData;

    public static final int SLOT_0_X = 60;
    public static final int SLOT_0_Y = 40;

    public static final int SLOT_1_X = 100;
    public static final int SLOT_1_Y = 40;

    public MenuToolModificationTable(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory,
                inventory.player.level().getBlockEntity(extraData.readBlockPos()),
                new SimpleContainerData(SLOT_COUNT));
    }

    public MenuToolModificationTable(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.TOOL_MODIFICATION_TABLE_MENU.get(), containerId);
        checkContainerSize(inventory, SLOT_COUNT);
        blockEntity = ((BlockEntityToolModificationTable) entity);
        this.level = inventory.player.level();
        this.containerData = data;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);


        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, SLOT_0_X, SLOT_0_Y));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, SLOT_1_X, SLOT_1_Y));
        });

        addDataSlots(data);
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 9; l++) {
                this.addSlot(new Slot(inventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    // made by claude ai
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack slotStack = slot.getItem();
        ItemStack original = slotStack.copy();

        if (index < SLOT_COUNT) {
            if (!moveItemStackTo(slotStack, SLOT_COUNT, slots.size(), true))
                return ItemStack.EMPTY;
            slot.onQuickCraft(slotStack, original);
        } else {
            if (!moveItemStackTo(slotStack, 0, SLOT_COUNT, false))
                return ItemStack.EMPTY;
        }

        if (slotStack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
        else slot.setChanged();
        if (slotStack.getCount() == original.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, slotStack);
        return original;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.TOOL_MODIFICATION_TABLE.get());
    }

    public void applyModifier(ServerPlayer player) {
        blockEntity.tryApplyModifier();
    }
}
