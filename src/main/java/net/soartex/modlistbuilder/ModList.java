package net.soartex.modlistbuilder;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import net.minecraft.client.Minecraft;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class ModList {
  @Mod.EventHandler
  public void loaded(FMLLoadCompleteEvent event) {
    File modListFile = new File(Minecraft.getMinecraft().mcDataDir, "modlist.txt");
    if (modListFile.exists()) return;

    ArrayList<String> modidBlacklist = new ArrayList<>();
    modidBlacklist.add(ModInfo.MOD_ID);
    modidBlacklist.add("mcp");
    modidBlacklist.add("forge");
    modidBlacklist.add("fml");

    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(modListFile));
      List<String> modList = new ArrayList<>();
      for (ModContainer modContainer : Loader.instance().getActiveModList()) {
        if (!modidBlacklist.contains(modContainer.getModId().toLowerCase())) {
          modList.add(modContainer.getModId());
        }
      }

      writer.write("{\n" +
          "  \"name\": \"My Amazing Modpack\",\n" +
          "  \"version\": \"1.0\",\n" +
          "  \"type\": \"modded_standard\",\n" +
          "  \"provider\": \"curse\",\n" +
          "  \"enabled\": true,\n" +
          "  \"is_public\": true,\n" +
          "  \"minecraft\": [\n" +
          "    \"1.7.x\"\n" +
          "  ],\n" +
          "  \"resourcepacks\": [\n" +
          "    \"fanver\"\n" +
          "  ],\n" +
          "  \"mods\": [\n");
      Collections.sort(modList, String.CASE_INSENSITIVE_ORDER);
      for (int i = 0; i < modList.size(); i++) {
        writer.write("    \"");
        writer.write(modList.get(i));
        writer.write("\"");
        if (i != modList.size() - 1) {
          writer.write(",");
        }
        writer.newLine();
      }
      writer.write("  ]\n" +
          "}");
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
