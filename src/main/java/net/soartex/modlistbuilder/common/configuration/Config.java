package net.soartex.modlistbuilder.common.configuration;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.soartex.modlistbuilder.ModList;
import net.soartex.modlistbuilder.common.GlobalConfig;
import net.soartex.modlistbuilder.common.ModInfo;

import java.io.File;

@net.minecraftforge.common.config.Config(modid = ModInfo.MOD_ID, name = "modlistbuilder/modlistbuilder")
public class Config {
  // General mod configs.
  @Comment({"Disable the auto creation of a modpack file."})
  public static boolean autoCreate = true;
  // General Modpack file configs.
  @Comment({"Set your modpack name for the mod to use when building your modpack file."})
  public static String modpackName = "My Amazing Modpack";
  @Comment({"Blacklisted mods to not include with the modpack json file."})
  public static String[] userBlacklistedMods = {};
}
