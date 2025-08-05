package dev.bebebea_loste.decorum.content.properties;

import net.minecraft.util.StringIdentifiable;

public enum FlowerPotState implements StringIdentifiable {
	SMALL("small"),
	MEDIUM("medium"),
	BIG("big"),
	CACTUS("cactus"),
	BAMBOO("bamboo"),
	AZALEA("azalea"),
	FLOWERING_AZALEA("flowering_azalea"),
	MANGROVE_PROPAGULE("mangrove_propagule");

	private final String id;

	FlowerPotState(String id) {
		this.id = id;
	}

	@Override
	public String asString() {
		return this.id;
	}
}
