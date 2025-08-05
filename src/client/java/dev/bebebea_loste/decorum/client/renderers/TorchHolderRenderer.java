package dev.bebebea_loste.decorum.client.renderers;

import dev.bebebea_loste.decorum.content.blockEntities.TorchHolderBlockEntity;
import dev.bebebea_loste.decorum.registries.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CandleBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import static dev.bebebea_loste.decorum.content.blocks.TorchHolder.MOUNTED;

public class TorchHolderRenderer implements BlockEntityRenderer<TorchHolderBlockEntity> {
	private final BlockRenderManager blockRenderManager;

	public TorchHolderRenderer(BlockEntityRendererFactory.Context context) {
		this.blockRenderManager = context.getRenderManager();
	}

	@Override
	public void render(TorchHolderBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
		if (!(entity.getStack().isEmpty())) {
			BlockModelRenderer modelRenderer = blockRenderManager.getModelRenderer();

			Block block = Registries.BLOCK.get(Registries.ITEM.getId(entity.getStack().getItem()));
			BlockState blockState = block.getDefaultState();
			if (entity.getStack().isIn(ItemTags.CANDLES)) {
				blockState = blockState.with(CandleBlock.LIT, true);
			}

			BlockStateModel model = blockRenderManager.getModel(block.getDefaultState());

			Vec3d torch_offset = new Vec3d(0, 0, 0);
			if (entity.getWorld() != null) {
				BlockState state = entity.getWorld().getBlockState(entity.getPos());
				if (state.isOf(Blocks.TORCH_HOLDER)) {
					if (state.get(MOUNTED)) {
						torch_offset = switch (state.get(Properties.HORIZONTAL_FACING)) {
							case NORTH -> new Vec3d(0, 4 / 16f, 5 / 16f);
							case SOUTH -> new Vec3d(0, 4 / 16f, -5 / 16f);
							case EAST -> new Vec3d(-5 / 16f, 4 / 16f, 0);
							case WEST -> new Vec3d(5 / 16f, 4 / 16f, 0);
							default -> torch_offset;
						};
					}
				}
			}

			matrices.push();
			matrices.translate(0, 0.125f, 0);
			matrices.translate(torch_offset);
			modelRenderer.render(
					entity.getWorld(),
					model.getParts(Random.create()),
					blockState,
					entity.getPos(),
					matrices,
					vertexConsumers.getBuffer(RenderLayer.getCutout()),
					true,
					0
			);
			matrices.pop();
		}
	}
}
