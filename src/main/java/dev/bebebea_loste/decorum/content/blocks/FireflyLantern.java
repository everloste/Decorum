package dev.bebebea_loste.decorum.content.blocks;

import dev.bebebea_loste.decorum.registries.Particles;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class FireflyLantern extends LanternBlock {

	public FireflyLantern(Settings settings) {
		super(settings.luminance((BlockState state) -> 7));
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {

		Vec3d vec3d = new Vec3d(pos.getX() + 0.5f, pos.getY() + 4.5/16f, pos.getZ() + 0.5f);
		if (state.get(HANGING)) {
			vec3d = vec3d.add(0f, 1/16f, 0f);
		}

		world.addParticleClient(ParticleTypes.SMALL_FLAME, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0F, 0.0F, 0.0F);
		if (random.nextFloat() < 0.25f) {
			world.addParticleClient(ParticleTypes.SMOKE, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0F, 0.0F, 0.0F);
		}

		if (world.isSkyVisible(pos.up())) {
			long timeOfDay = world.getTimeOfDay() % 24000;
			double amount = Math.pow(Math.cos(Math.abs(((double) timeOfDay / 18000) - 1)), 80)
					+ 0.1 * Math.pow(Math.cos(Math.abs(((double) timeOfDay / 12000) - 1)), 12);
			amount = amount * 5;

			if (amount >= 1) {
				for (int i = 0; i <= random.nextInt((int) amount); ++i) {
					// inside lantern: 0.175xz; 0.2y
					// outside: 1.5xz,; 1y
					float x = pos.getX() + 0.5f + (random.nextFloat() * 1.5f * (random.nextBoolean() ? -1 : 1));
					float z = pos.getZ() + 0.5f + (random.nextFloat() * 1.5f * (random.nextBoolean() ? -1 : 1));
					float y = pos.getY() + 0.25f + (random.nextFloat() * 1f * (random.nextBoolean() ? -1 : 1));

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
	}
}
