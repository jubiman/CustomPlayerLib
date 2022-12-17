package com.jubiman.customplayerlib;

import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;

public abstract class CustomPlayer {
	protected final long auth;

	public CustomPlayer(long auth) {
		this.auth = auth;
	}

	/**
	 * Creates new save component
	 * @return a SaveData component with name of the player's auth
	 */
	public SaveData generatePlayerSave() {
		return new SaveData(String.valueOf(auth));
	}

	/**
	 * Save the player's data
	 * @param save save parent object to add to
	 */
	public void addSaveData(SaveData save) {
		save.addLong("auth", auth);
	}

	/**
	 * Load player's data from saved data. Gets called before the rest of the player is loaded.
	 * @param data the data to load
	 */
	public abstract void loadEnter(LoadData data);

	/**
	 * Load player's data from saved data. Gets called before the rest of the player is loaded.
	 * @param data the data to load
	 */
	public abstract void loadExit(LoadData data);
}
