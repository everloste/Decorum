package dev.bebebea_loste.decorum.client.generators;

import dev.bebebea_loste.decorum.registries.Blocks;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ModelProvider extends FabricModelProvider {

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(Blocks.BAMBOO_FLOWER_POT.asItem(), Models.GENERATED);
        itemModelGenerator.register(Blocks.CANDLE_TRAY.asItem(), Models.GENERATED);
        itemModelGenerator.register(Blocks.ITEM_STAND.asItem(), Models.GENERATED);
    }
}
