package net.soartex.modlistbuilder.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import net.soartex.modlistbuilder.ModList;
import net.soartex.modlistbuilder.common.configuration.ConfigurationHelper;

import javax.annotation.Nullable;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ModPackFile {
  public String name = ConfigurationHelper.modpackName;
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
    ArrayList<String> userBlacklistedMods = new ArrayList<>(Arrays.asList(ConfigurationHelper.userBlacklistedMods));
    mods.clear();
    for (ModContainer modContainer : Loader.instance().getActiveModList()) {
      String modID = modContainer.getModId().toLowerCase();
      if (!modID.equals(ModInfo.MOD_ID.toLowerCase()) && !userBlacklistedMods.contains(modID) && !ConfigurationHelper.globalConfig.blacklisted.contains(modID)) {
        mods.add(modContainer.getModId());
      }
    }
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
