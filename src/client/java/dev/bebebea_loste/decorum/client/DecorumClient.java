package dev.bebebea_loste.decorum.client;

import dev.bebebea_loste.decorum.client.renderers.CandleTrayBlockEntityRenderer;
import dev.bebebea_loste.decorum.client.renderers.ItemStandBlockEntityRenderer;
import dev.bebebea_loste.decorum.registries.BlockEntities;
import dev.bebebea_loste.decorum.client.renderers.FlowerPotBlockEntityRenderer;
import dev.bebebea_loste.decorum.registries.Blocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class DecorumClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRendererFactories.register(BlockEntities.FLOWER_POT, FlowerPotBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(BlockEntities.CANDLE_TRAY_ENTITY, CandleTrayBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(BlockEntities.ITEM_STAND, ItemStandBlockEntityRenderer::new);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), Blocks.BAMBOO_FLOWER_POT, Blocks.CANDLE_TRAY, Blocks.ITEM_STAND);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), Blocks.CANDLE_TRAY);
	}
}
