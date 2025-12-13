package com.mewo.mewosgear.content.block.crafting.tool_modification_table;

import com.mewo.mewosgear.Main;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ScreenToolModificationTable extends AbstractContainerScreen<MenuToolModificationTable> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "textures/gui/tool_modification_table.png");

    public ScreenToolModificationTable(MenuToolModificationTable screen, Inventory inventory, Component component) {
        super(screen, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelX = 10000;
        this.inventoryLabelY = 10000;

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mX, int mY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageHeight, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mX, int mY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mX, mY, delta);
        renderTooltip(guiGraphics, mX, mY);
    }
}
