package dev.bebebea_loste.decorum.registries;

import dev.bebebea_loste.decorum.content.blocks.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CandleBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static dev.bebebea_loste.decorum.Mod.MOD_ID;

public class Blocks {

	// Decorative blocks
	public static Block BAMBOO_FLOWER_POT;
	public static Block TORCH_HOLDER;
	public static Block ITEM_STAND;
	public static Block FIREFLY_LANTERN;
	public static Block CANDLE_HOLDER;

	// Candles
	public static Block PRIDE_CANDLE;
	public static Block LESBIAN_PRIDE_CANDLE;
	public static Block TRANS_PRIDE_CANDLE;

	public static List<Block> BLOCKS = new ArrayList<Block>();
	public static List<Block> CANDLES = new ArrayList<Block>();

	public static void register() {
		BAMBOO_FLOWER_POT = registerBlock(
				true,
				"bamboo_flower_pot",
				BambooFlowerPot::new,
				BambooFlowerPot.Settings.create());
		TORCH_HOLDER = registerBlock(
				true,
				"torch_holder",
				TorchHolder::new,
				TorchHolder.Settings.create());
		ITEM_STAND = registerBlock(
				true,
				"item_stand",
				ItemStand::new,
				ItemStand.Settings.create());
		FIREFLY_LANTERN = registerBlock(
				true,
				"firefly_lantern",
				FireflyLantern::new,
				FireflyLantern.Settings.create());
		CANDLE_HOLDER = registerBlock(
				true,
				"candle_holder",
				CandleHolder::new,
				CandleHolder.Settings.create());

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> itemGroup.addAfter(Items.FLOWER_POT, TORCH_HOLDER.asItem()));
		//ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> itemGroup.addAfter(Items.FLOWER_POT, CANDLE_HOLDER.asItem()));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> itemGroup.addAfter(Items.FLOWER_POT, BAMBOO_FLOWER_POT.asItem()));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> itemGroup.addAfter(Items.ARMOR_STAND, ITEM_STAND.asItem()));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> itemGroup.addAfter(Items.SOUL_LANTERN, FIREFLY_LANTERN.asItem()));

		// CANDLES
		PRIDE_CANDLE = registerBlock(
				true,
				"pride_candle",
				CandleBlock::new,
				AbstractBlock.Settings.create().mapColor(MapColor.RED).nonOpaque().strength(0.1F).sounds(BlockSoundGroup.CANDLE).luminance(CandleBlock.STATE_TO_LUMINANCE).pistonBehavior(PistonBehavior.DESTROY));
		LESBIAN_PRIDE_CANDLE = registerBlock(
				true,
				"lesbian_pride_candle",
				CandleBlock::new,
				AbstractBlock.Settings.create().mapColor(MapColor.RED).nonOpaque().strength(0.1F).sounds(BlockSoundGroup.CANDLE).luminance(CandleBlock.STATE_TO_LUMINANCE).pistonBehavior(PistonBehavior.DESTROY));
		TRANS_PRIDE_CANDLE = registerBlock(
				true,
				"trans_pride_candle",
				CandleBlock::new,
				AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).nonOpaque().strength(0.1F).sounds(BlockSoundGroup.CANDLE).luminance(CandleBlock.STATE_TO_LUMINANCE).pistonBehavior(PistonBehavior.DESTROY));
		CANDLES.add(PRIDE_CANDLE); CANDLES.add(LESBIAN_PRIDE_CANDLE); CANDLES.add(TRANS_PRIDE_CANDLE);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> itemGroup.addAfter(Items.CANDLE, PRIDE_CANDLE.asItem()));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> itemGroup.addAfter(PRIDE_CANDLE, LESBIAN_PRIDE_CANDLE.asItem()));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> itemGroup.addAfter(LESBIAN_PRIDE_CANDLE, TRANS_PRIDE_CANDLE.asItem()));
	}

	private static Block registerBlock(Boolean register_item, String id_name, Function<AbstractBlock.Settings, Block> blockClassInstance, AbstractBlock.Settings settings) {
		Identifier id = Identifier.of(MOD_ID, id_name);
		RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, id);
		Block block = blockClassInstance.apply(settings.registryKey(blockKey));

		if (register_item) {
			Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, id))));
		}

		Block result = Registry.register(Registries.BLOCK, id, block);
		BLOCKS.add(result);
		return result;
	}
}
