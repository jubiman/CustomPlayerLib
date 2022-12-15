package com.jubiman.customplayerlib.patch;

import com.jubiman.customplayerlib.CustomPlayerRegistry;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.save.LoadData;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = ServerClient.class, name = "applySave", arguments = {LoadData.class})
public class LoadPatch {
	@Advice.OnMethodExit
	static void onExit(@Advice.Argument(0) LoadData loadData) {
		try {
			CustomPlayerRegistry.loadAll(loadData);
			/*CustomPlayerRegistry.forEach((key, value) -> {
				value.load(loadData.getLoadDataByName(key).get(0));
			});*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
