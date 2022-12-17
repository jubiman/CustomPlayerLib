package com.jubiman.customplayerlib;

import com.jubiman.customplayerlib.tick.ITickable;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;

public abstract class CustomPlayerTickable extends CustomPlayer implements ITickable {
	public CustomPlayerTickable(long auth) {
		super(auth);
	}

	/**
	 * Performs a server tick
	 * @param server the server to tick on
	 */
	@Override
	public abstract void serverTick(Server server);

	/**
	 * Performs a client tick
	 * @param client the client to tick on
	 */
	@Override
	public abstract void clientTick(Client client);
}
