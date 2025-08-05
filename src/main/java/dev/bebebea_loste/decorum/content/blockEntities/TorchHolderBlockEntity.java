package dev.bebebea_loste.decorum.content.blockEntities;

import dev.bebebea_loste.decorum.content.blockEntities.templates.SingleStackBlockEntity;
import dev.bebebea_loste.decorum.registries.BlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class TorchHolderBlockEntity extends SingleStackBlockEntity {
	public TorchHolderBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntities.TORCH_HOLDER, pos, state);
	}
}