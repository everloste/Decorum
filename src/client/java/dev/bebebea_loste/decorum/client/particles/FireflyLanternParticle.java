package dev.bebebea_loste.decorum.client.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class FireflyLanternParticle extends SpriteBillboardParticle {
	protected FireflyLanternParticle(ClientWorld clientWorld, double d, double e, double f) {
		super(clientWorld, d, e, f);
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	public void tick() {
		super.tick();
		float lifetime = (float) this.age / this.maxAge;
		this.setAlpha(1 - lifetime);
	}

	public int getBrightness(float tint) {
		float lifetime = (float) this.age / this.maxAge;
		return (int) (255.0F * (1 - lifetime));
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<SimpleParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			FireflyLanternParticle fireflyParticle = new FireflyLanternParticle(clientWorld, d, e, f);
			fireflyParticle.setMaxAge(clientWorld.random.nextBetween(128, 512));
			//fireflyParticle.scale(1.5F);
			fireflyParticle.setSprite(this.spriteProvider);
			fireflyParticle.setVelocity(g, h, i);
			fireflyParticle.collidesWithWorld = false;
			return fireflyParticle;
		}
	}
}
