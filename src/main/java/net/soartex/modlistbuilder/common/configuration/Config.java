package net.soartex.modlistbuilder.common.configuration;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.soartex.modlistbuilder.ModList;
import net.soartex.modlistbuilder.common.ModInfo;

import java.io.File;

import static net.soartex.modlistbuilder.common.configuration.ConfigurationHelper.*;

public class Config {
  public static Configuration config;
  public static File configDir;

  public Config() {
    config = new Configuration(new File(configDir, ModInfo.MOD_ID + ".cfg"));
    config.load();
    readConfig();
  }

  private void readConfig() {
    // General mod configs.
    autoCreate = config.getBoolean("Auto Create", Configuration.CATEGORY_GENERAL, true, "Disable the auto creation of" +
        " a modpack file.");

    // General Modpack file configs.
    modpackName = config.getString("Modpack Name", Configuration.CATEGORY_GENERAL, "My Amazing Modpack", "Set your" +
        " modpack name for the mod to use when building your modpack file.");
    userBlacklistedMods = config.getStringList("Blacklisted Mods", Configuration.CATEGORY_GENERAL, new String[]{},
        "Blacklisted mods to not include with the modpack json file.");

    if (config.hasChanged()) {
      ModList.logger.info("Saving config...");
      ModList.proxy.setModpackName(modpackName);
      config.save();
    }
  }

  @SubscribeEvent
  public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.modID.equalsIgnoreCase(ModInfo.MOD_ID)) {
      readConfig();
    }
  }
}
