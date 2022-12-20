package com.jubiman.customplayerlib;

import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;

/**
 * The instance of each individual player containing custom data
 */
public abstract class CustomPlayer {
	/**
	 * The authentication of the player, used to access this object in the CustomPlayers registry
	 */
	protected final long auth;

	/**
	 * Creates new CustomPlayer
	 * @param auth the authentication of the player
	 */
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
	public abstract void addSaveData(SaveData save);

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
