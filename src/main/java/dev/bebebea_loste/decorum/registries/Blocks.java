package dev.bebebea_loste.decorum.registries;

import dev.bebebea_loste.decorum.content.blocks.BambooFlowerPot;
import dev.bebebea_loste.decorum.content.blocks.CandleTray;
import dev.bebebea_loste.decorum.content.blocks.ItemStand;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static dev.bebebea_loste.decorum.Mod.MOD_ID;

public class Blocks {
	public static Block BAMBOO_FLOWER_POT;
	public static Block CANDLE_TRAY;
	public static Block ITEM_STAND;

	public static void register() {
		BAMBOO_FLOWER_POT = registerBlock(
				true,
				"bamboo_flower_pot",
				BambooFlowerPot::new,
				BambooFlowerPot.Settings.create()
		);
		CANDLE_TRAY = registerBlock(
				true,
				"candle_tray",
				CandleTray::new,
				CandleTray.Settings.create()
		);
		ITEM_STAND = registerBlock(
				true,
				"item_stand",
				ItemStand::new,
				ItemStand.Settings.create()
		);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> itemGroup.addAfter(Items.FLOWER_POT, BAMBOO_FLOWER_POT.asItem()));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> itemGroup.addAfter(Items.FLOWER_POT, CANDLE_TRAY.asItem()));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> itemGroup.addAfter(Items.ARMOR_STAND, ITEM_STAND.asItem()));

	}

	private static Block registerBlock(Boolean register_item, String id_name, Function<AbstractBlock.Settings, Block> blockClassInstance, AbstractBlock.Settings settings) {
		Identifier id = Identifier.of(MOD_ID, id_name);
		RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, id);
		Block block = blockClassInstance.apply(settings.registryKey(blockKey));

		if (register_item) {
			Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, id))));
		}

		return Registry.register(Registries.BLOCK, id, block);
	}
}
