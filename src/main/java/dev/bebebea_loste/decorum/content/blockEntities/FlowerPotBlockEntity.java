package dev.bebebea_loste.decorum.content.blockEntities;

import dev.bebebea_loste.decorum.content.blockEntities.templates.SingleStackBlockEntity;
import dev.bebebea_loste.decorum.registries.BlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class FlowerPotBlockEntity extends SingleStackBlockEntity {
	public FlowerPotBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntities.FLOWER_POT, pos, state);
	}
}
