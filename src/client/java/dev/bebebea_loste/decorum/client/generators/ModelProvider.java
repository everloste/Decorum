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
        for (int i = 0; i < Blocks.BLOCKS.size(); i++) {
            itemModelGenerator.register(Blocks.BLOCKS.get(i).asItem(), Models.GENERATED);
        }
    }
}
