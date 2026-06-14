package com.mewo.mewosgear.content.block.functional.chemicalfactory;

import com.mewo.mewosgear.Main;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import static com.mewo.mewosgear.content.block.functional.toolmodificationtable.BlockEntityToolModificationTable.SLOT_COUNT;

public class ScreenChemicalFactory extends AbstractContainerScreen<MenuChemicalFactory> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "texture/gui/blocks/chemical_factory.png");

    public ScreenChemicalFactory(MenuChemicalFactory menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    private void drawSlots(GuiGraphics graphics) {
        for (int i = 0; i < SLOT_COUNT; i++) {
            Slot slot = menu.slots.get(i);
            int x = leftPos + slot.x;
            int y = topPos + slot.y;
            graphics.fill(x - 1, y - 1, x + 17, y + 17, 0xFF525252);
        }

        for (int i = SLOT_COUNT; i < menu.slots.size(); i++) {
            Slot slot = menu.slots.get(i);
            int x = leftPos + slot.x;
            int y = topPos + slot.y;
            graphics.fill(x - 1, y - 1, x + 17, y + 17, 0xFF525252);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mX, int mY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mX, mY, delta);
        renderTooltip(graphics, mX, mY);
        drawSlots(graphics);
    }
}
