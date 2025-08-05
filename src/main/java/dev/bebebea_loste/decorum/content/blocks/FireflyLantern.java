package dev.bebebea_loste.decorum.content.blocks;

import dev.bebebea_loste.decorum.registries.Particles;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class FireflyLantern extends LanternBlock {

	public FireflyLantern(Settings settings) {
		super(settings.luminance((BlockState state) -> 9));
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		for (int i = 0; i <= random.nextInt(2); ++i) {
			float x = pos.getX() + 0.5f + (random.nextFloat() * 0.175f * (random.nextBoolean() ? -1 : 1));
			float z = pos.getZ() + 0.5f + (random.nextFloat() * 0.175f * (random.nextBoolean() ? -1 : 1));
			float y = pos.getY() + 0.25f + (random.nextFloat() * 0.2f * (random.nextBoolean() ? -1 : 1));

			if (random.nextBoolean()) {
				world.addParticleClient(
						Particles.FIREFLY_LANTERN,
						x,
						y,
						z,
						random.nextFloat() * 0.002f * (random.nextBoolean() ? -1 : 1),
						0.002f * random.nextFloat() * (random.nextBoolean() ? -1 : 1),
						random.nextFloat() * 0.002f * (random.nextBoolean() ? -1 : 1)
				);
			}
			world.addParticleClient(
					Particles.FIREFLY_LANTERN,
					x,
					y,
					z,
					random.nextFloat() * 0.015f * (random.nextBoolean() ? -1 : 1),
					0.015f * random.nextFloat() * (random.nextBoolean() ? -1 : 1),
					random.nextFloat() * 0.015f * (random.nextBoolean() ? -1 : 1)
			);
		}
	}
}
