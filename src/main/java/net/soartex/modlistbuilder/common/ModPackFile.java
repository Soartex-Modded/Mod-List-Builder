package net.soartex.modlistbuilder.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.soartex.modlistbuilder.ModList;
import net.soartex.modlistbuilder.common.configuration.Config;
import net.soartex.modlistbuilder.common.configuration.ConfigurationHelper;

import javax.annotation.Nullable;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ModPackFile {
  public String name = Config.modpackName;
  public String version = "0.0.1";
  public String type = "modded_standard";
  public String provider = "curse";
  public boolean enabled = true;
  public boolean is_public = true;
  public ArrayList<String> minecraft = new ArrayList<>();
  public ArrayList<String> resourcepacks = new ArrayList<>();
  public ArrayList<String> mods = new ArrayList<>();

  public ModPackFile() {
    minecraft.add(Loader.MC_VERSION);
    resourcepacks.add("fanver");
    resourcepacks.add("invictus");
  }

  @Nullable
  public static ModPackFile load(File file) throws IOException {
    BufferedReader bufferedReader = null;
    try {
      bufferedReader = new BufferedReader(new FileReader(file));
      return new Gson().fromJson(bufferedReader, ModPackFile.class);
    } catch (FileNotFoundException e) {
      ModList.logger.error("Failed to read modpack data from the file " + file.toString() + " please check the file is a valid JSON file and a modpack file generated from this mod.", e);
    } catch (JsonSyntaxException e) {
      ModList.logger.error("The modpack file " + file.toString() + " was not a valid JSON file.");
    } finally {
      if (bufferedReader != null) bufferedReader.close();
    }
    return null;
  }

  public void addMods() {
    mods.clear();
    for (ModContainer modContainer : Loader.instance().getActiveModList()) {
      String modId = getModId(modContainer);
      if (isNotBlacklisted(modId)) {
        mods.add(modId);
      }
    }
    mods.sort(String.CASE_INSENSITIVE_ORDER);
  }

  public boolean isNotBlacklisted(String modId) {
    ArrayList<String> userBlacklistedMods = new ArrayList<>(Arrays.asList(Config.userBlacklistedMods));
    return !modId.equals(ModInfo.MOD_ID.toLowerCase()) &&
        !userBlacklistedMods.contains(modId.toLowerCase()) &&
        !ConfigurationHelper.globalConfig.blacklisted.contains(modId.toLowerCase()) &&
        !mods.contains(modId);
  }

  public String getModId(ModContainer modContainer) {
    String modId = modContainer.getModId().toLowerCase();
    LinkedTreeMap modOverride = (LinkedTreeMap) ConfigurationHelper.globalConfig.overrides.get(modId);
    if (modOverride != null) {
      for (Object version : modOverride.keySet()) {
        if (version instanceof String) {
          if (modContainer.getVersion().startsWith((String) version)) {
            return (String) modOverride.get(version);
          }
        }
      }
    }
    return modContainer.getModId().split("\\|")[0];
  }

  public String toJson() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(this);
  }

  public boolean save(File file) {
    try {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
      bufferedWriter.write(this.toJson());
      bufferedWriter.close();
      return true;
    } catch (IOException e) {
      ModList.logger.error("Failed to write modpack data to file.", e);
    }
    return false;
  }
}
