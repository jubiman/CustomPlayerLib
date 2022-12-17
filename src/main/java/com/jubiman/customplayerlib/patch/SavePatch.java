package com.jubiman.customplayerlib.patch;

import com.jubiman.customplayerlib.CustomPlayerRegistry;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.save.SaveData;
import net.bytebuddy.asm.Advice;

/**
 * Saves all CustomPlayers
 */
@ModMethodPatch(target = ServerClient.class, name = "getSave", arguments = {})
public class SavePatch {
	@Advice.OnMethodExit
	static void onExit(@Advice.Return(readOnly = false) SaveData save) {
		CustomPlayerRegistry.saveAll(save);
	}
}
