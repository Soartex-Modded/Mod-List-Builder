package net.soartex.modlistbuilder.common.configuration;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.soartex.modlistbuilder.common.ModInfo;

import java.io.File;

import static net.soartex.modlistbuilder.common.configuration.ConfigurationHelper.blacklistedMods;

public class Config {
  public Configuration config;
  public static File configDir;

  public Config() {
    this.config = new Configuration(new File(configDir, ModInfo.MOD_ID + ".cfg"));
    config.load();
    readConfig();
  }

  private void readConfig() {
    blacklistedMods = config.getStringList("blacklistedMods", Configuration.CATEGORY_GENERAL, new String[]{
        "mcp", "forge", "fml", ModInfo.MOD_ID
    }, "Blacklisted mods to not include with the modpack json file.");

    if (config.hasChanged()) {
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
