package com.jubiman.customplayerlib;

import necesse.engine.GameEventListener;
import necesse.engine.GameEvents;
import necesse.engine.events.ServerClientDisconnectEvent;
import necesse.engine.events.ServerStopEvent;
import necesse.engine.network.server.ServerClient;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * Handler for handling custom players in your mod
 * @param <T> your CustomPlayer class
 */
public abstract class CustomPlayersHandler<T extends CustomPlayer> {
	private final HashMap<Long, T> userMap = new HashMap<>();
	private final Constructor<T> ctor;
	/**
	 * The name of the handler (usually tied to the mod name)
	 */
	protected final String name;

	/**
	 * Constructs the storage class for custom players
	 *
	 * @param clazz the class extending CustomPlayer
	 * @param name the name of the class, used for creating a save component
	 */
	public CustomPlayersHandler(Class<T> clazz, String name) {
		this.name = name;
		try {
			this.ctor = clazz.getConstructor(long.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}

		// Initialize event listener for ServerStopEvents, no need to call super when overwriting.
		GameEvents.addListener(ServerStopEvent.class, new GameEventListener<ServerStopEvent>() {
			@Override
			public void onEvent(ServerStopEvent e) {
				stop();
			}
		});
		GameEvents.addListener(ServerClientDisconnectEvent.class, new GameEventListener<ServerClientDisconnectEvent>() {
			@Override
			public void onEvent(ServerClientDisconnectEvent e) {
				userMap.remove(e.client.authentication);
			}
		});
	}

	/**
	 * A null safe way to get a player from the map, adds player if they don't exist yet
	 * @param auth the authentication of the player's ServerClient
	 * @return the object belonging to the player
	 */
	public T get(long auth) {
		try {
			if (!userMap.containsKey(auth))
				userMap.put(auth, ctor.newInstance(auth));
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException e) { // should only happen when people develop
			throw new RuntimeException(e);
		}
		return userMap.get(auth);
	}

	/**
	 * Iterate through the keys (player auths).
	 * @return a set of all keys (all player auths )
	 */
	public Set<Long> keyIterator() {
		return userMap.keySet();
	}

	/**
	 * Iterate through the values.
	 * @return a collection of all values (all CustomPlayers)
	 */
	public Collection<T> valueIterator() {
		return userMap.values();
	}

	/**
	 * Save player's data.
	 * @param saveData the parent save object (usually ServerClient)
	 * @param player the player to save
	 */
	public void save(SaveData saveData, ServerClient player) {
		SaveData save = new SaveData(name);
		get(player.authentication).addSaveData(save);
		saveData.addSaveData(save);
	}

	/**
	 * Load player from saved data. Gets called before the rest of the player is loaded.
	 * @param loadData data to load from (should be the same as where you save, usually ServerClient)
	 */
	public void loadEnter(LoadData loadData) {
		LoadData data = loadData.getLoadData().get(0);
		get(Long.parseLong(data.getName())).loadEnter(data);
	}

	/**
	 * Load player from saved data. Gets called after the rest of the player is loaded.
	 * @param loadData data to load from (should be the same as where you save, usually ServerClient)
	 */
	public void loadExit(LoadData loadData) {
		LoadData data = loadData.getLoadData().get(0);
		get(Long.parseLong(data.getName())).loadExit(data);
	}

	/**
	 * When switching worlds or on server stop this will be called to avoid overwriting data in other (older) worlds
	 */
	public void stop() {
		userMap.clear(); // avoid overwriting other worlds
	}
}
