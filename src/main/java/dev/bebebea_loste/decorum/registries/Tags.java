package dev.bebebea_loste.decorum.registries;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import static dev.bebebea_loste.decorum.Mod.MOD_ID;

public class Tags {
	public static final TagKey<Block> ITEMS_flower_pot_compatible = TagKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, "flower_pot_compatible"));
}
