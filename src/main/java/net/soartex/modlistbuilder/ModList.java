package net.soartex.modlistbuilder;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;
import net.soartex.modlistbuilder.common.ModInfo;
import net.soartex.modlistbuilder.common.ModPackFile;
import net.soartex.modlistbuilder.common.configuration.Config;
import net.soartex.modlistbuilder.common.configuration.ConfigurationHelper;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class ModList {
  public static Logger logger;
  public static Config config;
  public static ModPackFile modPackFile = null;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    Config.configDir = new File(event.getModConfigurationDirectory(), ModInfo.MOD_ID);
    config = new Config();
    logger = event.getModLog();
    FMLCommonHandler.instance().bus().register(config);
  }

  @Mod.EventHandler
  public void loadComplete(FMLLoadCompleteEvent event) {
    logger.info("Checking for existing modpack file.");
    File[] files = Config.configDir.listFiles();
    if (files != null) {
      for (File file : files) {
        if (FilenameUtils.getExtension(file.toString()).equals("json")) {
          logger.info("Found modpack file. Attempting to load modpack data.");
          modPackFile = ModPackFile.load(file);
          if (modPackFile != null) break;
        }
      }
    }
    if (modPackFile == null) {
      logger.info("Either no modpack file was found, or the modpack file was invalid. Creating a new modpack file instead.");
      modPackFile = new ModPackFile();
    }
    ArrayList<String> blacklistedMods = new ArrayList<>(Arrays.asList(ConfigurationHelper.blacklistedMods));
    for (ModContainer modContainer : Loader.instance().getActiveModList()) {
      if (!blacklistedMods.contains(modContainer.getModId().toLowerCase())) {
        modPackFile.mods.add(modContainer.getModId());
      }
    }
    logger.info("Modpack Name: " + modPackFile.name);
    logger.info("Saving modpack file...");
    if (modPackFile.save(new File(Config.configDir, modPackFile.name.replace(" ", "_").toLowerCase() + ".json"))) {
      logger.info("Successfully saved the modpack file.");
    } else {
      logger.error("Failed to save the modpack data to file.");
    }
  }
}
