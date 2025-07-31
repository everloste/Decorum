package dev.bebebea_loste.decorum.client.renderers;

import dev.bebebea_loste.decorum.content.blockEntities.CandleTrayBlockEntity;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class CandleTrayBlockEntityRenderer implements BlockEntityRenderer<CandleTrayBlockEntity> {
	private final BlockRenderManager blockRenderManager;

	public CandleTrayBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		this.blockRenderManager = context.getRenderManager();
	}

	@Override
	public void render(CandleTrayBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
		if (!(entity.getStack().isEmpty())) {
			matrices.push();
			Block block = Registries.BLOCK.get(Registries.ITEM.getId(entity.getStack().getItem()));
			BlockStateModel model = blockRenderManager.getModel(block.getDefaultState());

			matrices.translate(0, 0.125, 0);


			BlockState blockState = block.getDefaultState();

			if (entity.getStack().isIn(ItemTags.CANDLES)) {
				blockState = block.getDefaultState().with(CandleBlock.LIT, true);
			}

			BlockModelRenderer abc = blockRenderManager.getModelRenderer();
			abc.render(
					entity.getWorld(),
					model.getParts(Random.create()),
					blockState,
					entity.getPos(),
					matrices,
					vertexConsumers.getBuffer(RenderLayer.getCutout()),
					true,
					0
			);


			//blockRenderManager.renderBlock(block.getDefaultState(), entity.getPos(), entity.getWorld(), matrices, vertexConsumers.getBuffer(RenderLayer.getTranslucent()), true, model.getParts(Random.create()));
			matrices.pop();


		}
	}
}
