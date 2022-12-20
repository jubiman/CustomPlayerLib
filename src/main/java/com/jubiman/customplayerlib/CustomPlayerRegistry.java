package com.jubiman.customplayerlib;

import com.jubiman.customplayerlib.tick.ITickable;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Registry where mods can register CustomPlayers
 */
public class CustomPlayerRegistry {
	private static final HashMap<String, CustomPlayersHandler<? extends CustomPlayer>> registry = new HashMap<>();

	/**
	 * Register a new CustomPlayers instance
	 * @param identifier the name of the class (used to return the instance)
	 * @param customPlayersHandlerInstance a new instance of the class
	 */
	public static void register(String identifier, CustomPlayersHandler<?> customPlayersHandlerInstance) {
		registry.put(identifier, customPlayersHandlerInstance);
	}

	/**
	 * Get CustomPlayers instance in registry
	 * @param identifier the identifier of the CustomPlayers
	 * @return CustomPlayers instance registered with the identifier
	 */
	public static CustomPlayersHandler<?> get(String identifier) {
		return registry.get(identifier);
	}

	/**
	 * Applies forEach function on the registry HasMap
	 * @param action the function to execute
	 */
	public static void forEach(BiConsumer<? super String, ? super CustomPlayersHandler<?>> action) {
		registry.forEach(action);
	}

	/**
	 * Saves player data from all registered CustomPlayers classes
	 * @param save the SaveData to save to
	 * @param player the player to save
	 */
	public static void saveAll(SaveData save, ServerClient player) {
		for (CustomPlayersHandler<?> cps : registry.values())
			cps.save(save, player);
	}

	/**
	 * Loads all registered CustomPlayers, is called before the rest of the player is loaded
	 * @param data the LoadData to load from
	 */
	public static void loadAllEnter(LoadData data) {
		for (Map.Entry<String, CustomPlayersHandler<?>> entry : registry.entrySet())
			entry.getValue().loadEnter(data.getLoadDataByName(entry.getKey()).get(0));
	}

	/**
	 * Loads all registered CustomPlayers, is called after the rest of the player is loaded
	 * @param data the LoadData to load from
	 */
	public static void loadAllExit(LoadData data) {
		for (Map.Entry<String, CustomPlayersHandler<?>> entry : registry.entrySet())
			entry.getValue().loadExit(data.getLoadDataByName(entry.getKey()).get(0));
	}

	/**
	 * Server ticks all registered CustomPlayers. Please do not call this function as it's called every tick when Necesse's server ticks.
	 * @param server the server to tick from
	 */
	public static void serverTickAll(Server server) {
		for (CustomPlayersHandler<?> cps : registry.values())
			if (cps instanceof ITickable)
				((ITickable) cps).serverTick(server);
	}

	/**
	 * Client ticks all registered CustomPlayers. Please do not call this function as it's called every tick when Necesse's client ticks.
	 * @param client the client to tick from
	 */
	public static void clientTickAll(Client client) {
		for (CustomPlayersHandler<?> cps : registry.values())
			if (cps instanceof ITickable)
				((ITickable) cps).clientTick(client);
	}
}
