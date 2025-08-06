package dev.bebebea_loste.decorum.client;

import dev.bebebea_loste.decorum.client.particles.FireflyLanternParticle;
import dev.bebebea_loste.decorum.client.renderers.CandleHolderRenderer;
import dev.bebebea_loste.decorum.client.renderers.TorchHolderRenderer;
import dev.bebebea_loste.decorum.client.renderers.ItemStandBlockEntityRenderer;
import dev.bebebea_loste.decorum.registries.BlockEntities;
import dev.bebebea_loste.decorum.client.renderers.FlowerPotBlockEntityRenderer;
import dev.bebebea_loste.decorum.registries.Blocks;
import dev.bebebea_loste.decorum.registries.Particles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class DecorumClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRendererFactories.register(BlockEntities.FLOWER_POT, FlowerPotBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(BlockEntities.TORCH_HOLDER, TorchHolderRenderer::new);
		BlockEntityRendererFactories.register(BlockEntities.ITEM_STAND, ItemStandBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(BlockEntities.CANDLE_HOLDER, CandleHolderRenderer::new);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), Blocks.BAMBOO_FLOWER_POT, Blocks.TORCH_HOLDER, Blocks.ITEM_STAND, Blocks.CANDLE_HOLDER);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), Blocks.TORCH_HOLDER, Blocks.FIREFLY_LANTERN);
		ParticleFactoryRegistry.getInstance().register(Particles.FIREFLY_LANTERN, FireflyLanternParticle.Factory::new);
		//ModelLoadingPlugin.register(this);
	}
}
