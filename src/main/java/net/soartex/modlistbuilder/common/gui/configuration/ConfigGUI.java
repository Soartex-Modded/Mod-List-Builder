package net.soartex.modlistbuilder.common.gui.configuration;

import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.soartex.modlistbuilder.common.ModInfo;
import net.soartex.modlistbuilder.common.configuration.Config;

import java.util.ArrayList;
import java.util.List;

public class ConfigGUI extends GuiConfig {

  public ConfigGUI(GuiScreen guiScreen) {
    super(guiScreen, getConfigElements(), ModInfo.MOD_ID, false, false, "Mod List Builder Configuration");
  }

  private static List<IConfigElement> getConfigElements() {
    List<IConfigElement> configElements = new ArrayList<>();
    configElements.add(configElement(Configuration.CATEGORY_GENERAL, "General", "gui.config.general"));
    return configElements;
  }

  private static IConfigElement configElement(String category, String name, String tooltipKey) {
    return new DummyConfigElement.DummyCategoryElement(name, tooltipKey,
        new ConfigElement(Config.config.getCategory(category)).getChildElements());
  }
}
