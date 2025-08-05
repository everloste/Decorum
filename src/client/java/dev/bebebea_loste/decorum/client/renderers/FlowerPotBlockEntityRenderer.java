package dev.bebebea_loste.decorum.client.renderers;

import dev.bebebea_loste.decorum.content.blockEntities.FlowerPotBlockEntity;
import dev.bebebea_loste.decorum.content.blocks.BambooFlowerPot;
import dev.bebebea_loste.decorum.content.properties.FlowerPotState;
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
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class FlowerPotBlockEntityRenderer implements BlockEntityRenderer<FlowerPotBlockEntity> {
	private final BlockRenderManager blockRenderManager;

	public FlowerPotBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		this.blockRenderManager = context.getRenderManager();
	}

	@Override
	public void render(FlowerPotBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
		if (!(entity.getStack().isEmpty())) {
			Block block = Registries.BLOCK.get(Registries.ITEM.getId(entity.getStack().getItem()));
			BlockStateModel model = blockRenderManager.getModel(block.getDefaultState());

			Vec3d abcd = block.getDefaultState().getModelOffset(entity.getPos());

			if (entity.getWorld() != null) {
				BlockState state = entity.getWorld().getBlockState(entity.getPos());
				if (state.isOf(Blocks.BAMBOO_FLOWER_POT)) {
					if (state.get(BambooFlowerPot.FLOWER_POT_TYPE) == FlowerPotState.SMALL || state.get(BambooFlowerPot.FLOWER_POT_TYPE) == FlowerPotState.MEDIUM || state.get(BambooFlowerPot.FLOWER_POT_TYPE) == FlowerPotState.BIG) {
						matrices.push();
						matrices.translate(-abcd.x + (0.125), 0.25, -abcd.z + (0.125));
						matrices.scale(0.75F, 0.75F, 0.75F);

						BlockModelRenderer abc = blockRenderManager.getModelRenderer();
						abc.render(
								entity.getWorld(),
								model.getParts(Random.create()),
								block.getDefaultState(),
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
		}
	}
}
