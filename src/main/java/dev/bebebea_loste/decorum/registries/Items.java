package dev.bebebea_loste.decorum.registries;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class Items {
	public static BlockItem BAMBOO_FLOWER_POT;
	public static BlockItem CANDLE_TRAY;

	public static void register() {
		//BAMBOO_FLOWER_POT = new BlockItem(Blocks.BAMBOO_FLOWER_POT, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Blocks.BAMBOO_FLOWER_POT_ID)));
		//Registry.register(Registries.ITEM, RegistryKey.of(RegistryKeys.ITEM, Blocks.BAMBOO_FLOWER_POT_ID), BAMBOO_FLOWER_POT);
	}
}
