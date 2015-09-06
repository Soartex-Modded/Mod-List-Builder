package net.soartex.modlistbuilder.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import net.soartex.modlistbuilder.ModList;

import javax.annotation.Nullable;
import java.io.*;
import java.rmi.MarshalledObject;
import java.rmi.server.ExportException;
import java.util.ArrayList;

public class ModPackFile {
  public String name = "My Amazing Modpack";
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

  @Nullable
  public static ModPackFile load(File file) {
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
      return new Gson().fromJson(bufferedReader, ModPackFile.class);
    } catch (FileNotFoundException e) {
      ModList.logger.error("Failed to read modpack data from file. File:" + file.toString(), e);
    } catch (JsonSyntaxException e) {
      ModList.logger.error("Modpack file " + file.toString() + " was not a valid JSON file.");
    }
    return null;
  }
}
