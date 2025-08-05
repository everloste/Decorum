package dev.bebebea_loste.decorum.registries;

import dev.bebebea_loste.decorum.content.blockEntities.CandleHolderBlockEntity;
import dev.bebebea_loste.decorum.content.blockEntities.TorchHolderBlockEntity;
import dev.bebebea_loste.decorum.content.blockEntities.FlowerPotBlockEntity;
import dev.bebebea_loste.decorum.content.blockEntities.ItemStandBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static dev.bebebea_loste.decorum.Mod.MOD_ID;

public class BlockEntities {

	public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MOD_ID, path), blockEntityType);
	}

	public static final BlockEntityType<FlowerPotBlockEntity> FLOWER_POT = register(
			"flower_pot",
			FabricBlockEntityTypeBuilder.create(FlowerPotBlockEntity::new, Blocks.BAMBOO_FLOWER_POT).build()
	);

	public static final BlockEntityType<TorchHolderBlockEntity> TORCH_HOLDER = register(
			"torch_holder",
			FabricBlockEntityTypeBuilder.create(TorchHolderBlockEntity::new, Blocks.TORCH_HOLDER).build()
	);
	public static final BlockEntityType<ItemStandBlockEntity> ITEM_STAND = register(
			"item_display",
			FabricBlockEntityTypeBuilder.create(ItemStandBlockEntity::new, Blocks.ITEM_STAND).build()
	);
	public static final BlockEntityType<CandleHolderBlockEntity> CANDLE_HOLDER = register(
			"candle_holder",
			FabricBlockEntityTypeBuilder.create(CandleHolderBlockEntity::new, Blocks.CANDLE_HOLDER).build()
	);

	public static void initialize() {

	}

}
