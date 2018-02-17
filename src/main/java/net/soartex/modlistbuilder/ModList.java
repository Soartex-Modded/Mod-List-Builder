package net.soartex.modlistbuilder;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.soartex.modlistbuilder.command.MLBCommand;
import net.soartex.modlistbuilder.common.CommonProxy;
import net.soartex.modlistbuilder.common.ModInfo;
import net.soartex.modlistbuilder.common.configuration.Config;
import net.soartex.modlistbuilder.common.configuration.ConfigurationHelper;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class ModList {
  public static Logger logger;

  @SidedProxy(clientSide = ModInfo.MOD_CLIENT_PROXY, serverSide = ModInfo.MOD_COMMON_PROXY)
  public static CommonProxy proxy;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    ConfigurationHelper.configDir = new File(event.getModConfigurationDirectory(), ModInfo.MOD_ID);
    new Config();
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
