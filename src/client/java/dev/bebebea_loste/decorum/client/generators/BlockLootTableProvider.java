package dev.bebebea_loste.decorum.client.generators;

import dev.bebebea_loste.decorum.registries.Blocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.client.data.Models;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {

    public BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        for (int i = 0; i < Blocks.BLOCKS.size(); i++) {
            if (!Blocks.CANDLES.contains(Blocks.BLOCKS.get(i))) {
                addDrop(Blocks.BLOCKS.get(i));
            }
        }
    }
}