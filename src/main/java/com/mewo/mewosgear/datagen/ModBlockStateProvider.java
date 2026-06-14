package com.mewo.mewosgear.datagen;

import com.mewo.mewosgear.Main;
import com.mewo.mewosgear.content.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Main.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.TOOL_MODIFICATION_TABLE, "crafting");
        blockWithItem(ModBlocks.CHEMICAL_FACTORY, "crafting");
    }

    private void blockWithItem(RegistryObject<? extends Block> block, String category) {
        String name = block.getId().getPath();
        simpleBlockWithItem(
                block.get(),
                models().cubeAll(name, modLoc("block/" + category + "/" + name + "/all")));
    }
}
