package net.soartex.modlistbuilder.client;

import net.soartex.modlistbuilder.ModList;
import net.soartex.modlistbuilder.common.CommonProxy;
import net.soartex.modlistbuilder.common.ModPackFile;
import net.soartex.modlistbuilder.common.configuration.Config;
import net.soartex.modlistbuilder.common.configuration.ConfigurationHelper;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

public class ClientProxy extends CommonProxy {
  public static ModPackFile modPackFile = null;

  @Override
  public void loadComplete() {
    new GlobalConfigDownloader();
  }

  @Override
  public void generateModpackFile() {
    ModList.logger.info("Checking for existing modpack file.");
    File[] files = ConfigurationHelper.configDir.listFiles();
    if (files != null) {
      for (File file : files) {
        if (FilenameUtils.getExtension(file.toString()).equals("json")) {
          ModList.logger.info("Found the modpack file " + file.toString() + " attempting to load the modpack data.");
          try {
            modPackFile = ModPackFile.load(file);
          } catch (IOException e) {
            ModList.logger.error("Failed to close the modpack file after reading from it.", e);
          }
          if (modPackFile != null) break;
        }
      }
    }
    if (modPackFile == null) {
      ModList.logger.info("Either no modpack file was found, or the modpack file was invalid. Creating a new modpack file instead.");
      modPackFile = new ModPackFile();
    } else {
      Config.modpackName = modPackFile.name;
    }
    modPackFile.addMods();
    if (!Config.autoCreate) return;
    ModList.logger.info("Saving modpack file...");
    if (modPackFile.save(new File(ConfigurationHelper.configDir, modPackFile.name.replace(" ", "_").toLowerCase() + ".json"))) {
      ModList.logger.info("Successfully saved the modpack file.");
    } else {
      ModList.logger.error("Failed to save the modpack data to file.");
    }
  }

  @Override
  public void setModpackName(String name) {
    if (modPackFile != null) modPackFile.name = name;
  }
}
