package dev.bebebea_loste.decorum.content.blockEntities;

import dev.bebebea_loste.decorum.content.blockEntities.templates.SingleStackBlockEntity;
import dev.bebebea_loste.decorum.registries.BlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class CandleTrayBlockEntity extends SingleStackBlockEntity {
	public CandleTrayBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntities.CANDLE_TRAY_ENTITY, pos, state);
	}
}