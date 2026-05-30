package com.mewo.mewosgear.dataGen;

import com.mewo.mewosgear.Main;
import com.mewo.mewosgear.registry.Item.ModWeapons;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModWeapons.STEEL_SWORD);
    }

    @SuppressWarnings("removal")
    private ItemModelBuilder simpleItem(RegistryObject<? extends Item> itemRegistryObject) {
        return withExistingParent(itemRegistryObject.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Main.MOD_ID, "item/" + itemRegistryObject.getId().getPath()));
    }
}
