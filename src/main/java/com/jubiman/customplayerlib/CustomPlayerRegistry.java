package com.jubiman.customplayerlib;

import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class CustomPlayerRegistry {
	private static final HashMap<String, CustomPlayers<?>> registry = new HashMap<>();

	/**
	 * Register a new CustomPlayers instance
	 * @param identifier the name of the class (used to return the instance)
	 * @param customPlayersInstance a new instance of the class
	 */
	public static void register(String identifier, CustomPlayers<?> customPlayersInstance) {
		registry.put(identifier, customPlayersInstance);
	}

	/**
	 * Get CustomPlayers instance in registry
	 * @param identifier the identifier of the CustomPlayers
	 * @return CustomPlayers instance registered with the identifier
	 */
	public static CustomPlayers<?> get(String identifier) {
		return registry.get(identifier);
	}

	/**
	 * Applies forEach function on the registry HasMap
	 * @param action the function to execute
	 */
	public static void forEach(BiConsumer<? super String, ? super CustomPlayers<?>> action) {
		registry.forEach(action);
	}

	public static void saveAll(SaveData save) {
		for (CustomPlayers<?> cps : registry.values())
			cps.save(save);
	}

	public static void loadAll(LoadData data) {
		for (Map.Entry<String, CustomPlayers<?>> entry : registry.entrySet())
			entry.getValue().load(data.getLoadDataByName(entry.getKey()).get(0));
	}
}
