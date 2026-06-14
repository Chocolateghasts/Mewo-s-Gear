package com.mewo.mewosgear.content.block.functional.chemicalfactory;

import com.mewo.mewosgear.content.registry.ModBlocks;
import com.mewo.mewosgear.content.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

import static com.mewo.mewosgear.content.block.functional.chemicalfactory.BlockEntityChemicalFactory.SLOT_COUNT;

public class MenuChemicalFactory extends AbstractContainerMenu {
    public final BlockEntityChemicalFactory blockEntity;
    private final Level level;
    private ContainerData containerData;

    public MenuChemicalFactory(int containerId, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(friendlyByteBuf.readBlockPos()),
                new SimpleContainerData(SLOT_COUNT));
    }

    public MenuChemicalFactory(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.CHEMICAL_FACTORY_MENU.get(), containerId);
        checkContainerSize(inventory, SLOT_COUNT);
        blockEntity = (BlockEntityChemicalFactory) entity;
        this.level = inventory.player.level();
        this.containerData = data;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 10, 100));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 100, 100));
        });
        System.out.println("created menu atually");
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
}
