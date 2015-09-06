package net.soartex.modlistbuilder;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.soartex.modlistbuilder.command.MLBCommand;
import net.soartex.modlistbuilder.common.CommonProxy;
import net.soartex.modlistbuilder.common.ModInfo;
import net.soartex.modlistbuilder.common.configuration.Config;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION, guiFactory = ModInfo.GUI_FACTORY)
public class ModList {
  public static Logger logger;
  public static Config config;

  @SidedProxy(clientSide = ModInfo.MOD_CLIENT_PROXY, serverSide = ModInfo.MOD_COMMON_PROXY)
  public static CommonProxy proxy;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    Config.configDir = new File(event.getModConfigurationDirectory(), ModInfo.MOD_ID);
    config = new Config();
    FMLCommonHandler.instance().bus().register(config);
  }

  @Mod.EventHandler
  public void serverLoad(FMLServerStartingEvent event) {
    event.registerServerCommand(new MLBCommand());
  }

  @Mod.EventHandler
  public void loadComplete(FMLLoadCompleteEvent event) {
    proxy.loadComplete();
  }
}
