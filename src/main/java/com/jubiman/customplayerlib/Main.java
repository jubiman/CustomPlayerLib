package com.jubiman.customplayerlib;

import necesse.engine.modLoader.annotations.ModEntry;

/**
 * This class is the ModEntry so that Necesse loads this mod.
 * The reason this library is a mod is so the registry can live inside Necesse and all dependent mods have only one central Registry object,
 * instead of one Registry object per mod.
 */
@ModEntry
public class Main { // just so it's loaded to upload it to steam workshop
}
