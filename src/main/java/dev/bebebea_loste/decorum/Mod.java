package dev.bebebea_loste.decorum;

import dev.bebebea_loste.decorum.registries.BlockEntities;
import dev.bebebea_loste.decorum.registries.Blocks;
import dev.bebebea_loste.decorum.registries.Items;
import dev.bebebea_loste.decorum.registries.Particles;
import net.fabricmc.api.ModInitializer;

public class Mod implements ModInitializer {
	public static final String MOD_ID = "decorum";

	@Override
	public void onInitialize() {
		Blocks.register();
		Items.register();
		BlockEntities.initialize();
		Particles.register();
	}
}
