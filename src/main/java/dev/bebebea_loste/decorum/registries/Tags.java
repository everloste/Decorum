package dev.bebebea_loste.decorum.registries;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import static dev.bebebea_loste.decorum.Mod.MOD_ID;

public class Tags {
	public static final TagKey<Item> ITEMS_COMPATIBLE_WITH_FLOWER_POT = TagKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "flower_pot/compatible"));
	public static final TagKey<Item> ITEMS_USING_BIG_FLOWER_POT = TagKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "flower_pot/uses_big"));
}
