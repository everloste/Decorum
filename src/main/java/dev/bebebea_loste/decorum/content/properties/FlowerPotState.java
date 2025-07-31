package dev.bebebea_loste.decorum.content.properties;

import net.minecraft.util.StringIdentifiable;

public enum FlowerPotState implements StringIdentifiable {
	REGULAR("regular"),
	WIDE("wide");

	private final String id;

	FlowerPotState(String id) {
		this.id = id;
	}

	@Override
	public String asString() {
		return this.id;
	}
}
