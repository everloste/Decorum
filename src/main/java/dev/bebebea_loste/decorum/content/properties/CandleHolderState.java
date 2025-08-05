package dev.bebebea_loste.decorum.content.properties;

import net.minecraft.util.StringIdentifiable;

public enum CandleHolderState implements StringIdentifiable {
	STANDING ("standing"),
	MOUNTED ("mounted"),
	HANGING ("hanging");

	private final String id;

	CandleHolderState(String id) {
		this.id = id;
	}

	@Override
	public String asString() {
		return this.id;
	}
}

