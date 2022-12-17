package com.jubiman.customplayerlib.patch;

import com.jubiman.customplayerlib.CustomPlayerRegistry;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.save.LoadData;
import net.bytebuddy.asm.Advice;

/**
 * Loads all CustomPlayers
 */
@ModMethodPatch(target = ServerClient.class, name = "applySave", arguments = {LoadData.class})
public class LoadPatch {
	@Advice.OnMethodExit
	static void onExit(@Advice.Argument(0) LoadData loadData) {
		try {
			CustomPlayerRegistry.loadAll(loadData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
