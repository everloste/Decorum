package dev.bebebea_loste.decorum.client.renderers;

import dev.bebebea_loste.decorum.content.blockEntities.CandleHolderBlockEntity;
import dev.bebebea_loste.decorum.content.blocks.CandleHolder;
import dev.bebebea_loste.decorum.content.properties.CandleHolderState;
import dev.bebebea_loste.decorum.registries.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class CandleHolderRenderer implements BlockEntityRenderer<CandleHolderBlockEntity> {
	private final BlockRenderManager blockRenderManager;

	public CandleHolderRenderer(BlockEntityRendererFactory.Context context) {
		this.blockRenderManager = context.getRenderManager();
	}

	@Override
	public void render(CandleHolderBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
		BlockModelRenderer modelRenderer = blockRenderManager.getModelRenderer();
		//int candleCount = entity.getCandleCount();
		BlockPos pos = entity.getPos();
		World world = entity.getWorld();

		if (world != null) {
			BlockState state = world.getBlockState(pos);

			if (state.isOf(Blocks.CANDLE_HOLDER)) {
				int candleSlotCount = state.get(CandleHolder.CANDLE_SLOTS);
				CandleHolderState candleHolderState = state.get(CandleHolder.CANDLE_HOLDER_STATE);
				Direction facing = state.get(Properties.FACING);
				boolean lit = state.get(Properties.LIT);
				int rotationDeg = 0;
				boolean rotate = false;

				switch (facing) {
					case NORTH: break;
					case SOUTH: rotate = false; break;
					case EAST: rotate = true; break;
					case WEST: rotate = true; break;
				}

				switch (candleHolderState) {
					case STANDING: {
						switch (candleSlotCount) {
							case 1:
								renderCandle(0, lit, new Vec3d(0, (double) 6/16, 0), modelRenderer, entity, matrices, vertexConsumers, overlay);
								break;
							case 2:
								renderCandle(0, lit, new Vec3d((double) 4/16 * (rotate ? 0 : 1), (double) 7/16, (double) 4/16 * (rotate ? 1 : 0)), modelRenderer, entity, matrices, vertexConsumers, overlay);
								renderCandle(1, lit, new Vec3d((double) -4/16 * (rotate ? 0 : 1), (double) 7/16, (double) -4/16 * (rotate ? 1 : 0)), modelRenderer, entity, matrices, vertexConsumers, overlay);
								break;
							case 3:
								renderCandle(0, lit, new Vec3d(0, (double) 6/16, 0), modelRenderer, entity, matrices, vertexConsumers, overlay);
								renderCandle(1, lit, new Vec3d((double) 4/16, (double) 7/16, 0), modelRenderer, entity, matrices, vertexConsumers, overlay);
								renderCandle(2, lit, new Vec3d((double) -4/16, (double) 7/16, 0), modelRenderer, entity, matrices, vertexConsumers, overlay);
								break;
							case 4:
								break;
						}
						break;
					}
					case HANGING: {
						break;
					}
					case MOUNTED: {
						break;
					}
				}
			}
		}
	}

	private void renderCandle(int candleIndex, boolean lit, Vec3d offset, BlockModelRenderer renderer, CandleHolderBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int overlay) {
		Block candle = entity.getCandleAsBlock(candleIndex);

		if (candle != null) {
			BlockState candleState = candle.getDefaultState().with(Properties.LIT, false);
			if (lit) { candleState = candleState.with(Properties.LIT, true); }
			BlockStateModel model = blockRenderManager.getModel(candleState);

			matrices.push();
			//matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
			matrices.translate(offset);
			renderer.render(
					entity.getWorld(),
					model.getParts(Random.create()),
					candleState,
					entity.getPos(),
					matrices,
					vertexConsumers.getBuffer(RenderLayer.getCutout()),
					true,
					overlay
			);
			matrices.pop();
		}
	}
}