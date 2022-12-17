package com.jubiman.customplayerlib;

import com.jubiman.customplayerlib.tick.ITickable;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;

public class CustomPlayersTickable<T extends CustomPlayerTickable> extends CustomPlayers<T> implements ITickable {
	/**
	 * Constructs the tickable storage class for custom players
	 * @param clazz the class extending CustomPlayer
	 * @param name  the name of the class, used for creating a save component
	 */
	public CustomPlayersTickable(Class<T> clazz, String name) {
		super(clazz, name);
	}

	/**
	 * Server ticks all players in the userMap
	 * @param server the server to perform the tick on
	 */
	@Override
	public void serverTick(Server server) {
		for (CustomPlayerTickable cp : valueIterator())
			cp.serverTick(server);
	}

	/**
	 * Client ticks all players in the userMap
	 * @param client the client to perform the tick on
	 */
	@Override
	public void clientTick(Client client) {
		for (CustomPlayerTickable cp : valueIterator())
			cp.clientTick(client);
	}
}
