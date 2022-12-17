package com.jubiman.customplayerlib.patch;

import com.jubiman.customplayerlib.CustomPlayerRegistry;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.Server;
import necesse.entity.mobs.PlayerMob;
import net.bytebuddy.asm.Advice;

/**
 * Server ticks all players that implement ITickable
 */
@ModMethodPatch(target = Server.class, name = "tick", arguments = {})
public class ServerTickPatch {
	@Advice.OnMethodExit
	static void onExit(@Advice.This Server server) {
		for (int i = 0; i < server.getPlayersOnline(); ++i) {
			PlayerMob player = server.getPlayer(i);
			if (player.isServerClient())
				CustomPlayerRegistry.serverTickAll(server);
		}
	}
}
