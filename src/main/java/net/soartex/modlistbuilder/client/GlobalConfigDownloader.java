package net.soartex.modlistbuilder.client;

import net.soartex.modlistbuilder.ModList;
import net.soartex.modlistbuilder.common.GlobalConfig;
import net.soartex.modlistbuilder.common.ModInfo;
import net.soartex.modlistbuilder.common.configuration.Config;
import net.soartex.modlistbuilder.common.configuration.ConfigurationHelper;

public class GlobalConfigDownloader extends Thread {
  public GlobalConfigDownloader() {
    setName("Mod List Builder Global Thread");
    setDaemon(true);
    start();
  }

  @Override
  public void run() {
    ModList.logger.info("Attempting to download the global config.");
    ConfigurationHelper.globalConfig = GlobalConfig.load(ModInfo.GLOBAL_CONFIG_URL);
    ModList.proxy.generateModpackFile();
  }
}
