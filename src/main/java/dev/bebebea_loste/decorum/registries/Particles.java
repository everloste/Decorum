package dev.bebebea_loste.decorum.registries;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static dev.bebebea_loste.decorum.Mod.MOD_ID;

public class Particles {
	public static final SimpleParticleType FIREFLY_LANTERN = FabricParticleTypes.simple();

	public static void register() {
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "firefly_lantern"), FIREFLY_LANTERN);
	}
}
