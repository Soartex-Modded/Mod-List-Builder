package net.soartex.modlistbuilder.common;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import net.soartex.modlistbuilder.ModList;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GlobalConfig {
  public ArrayList<String> blacklisted = new ArrayList<>();
  public LinkedTreeMap overrides = new LinkedTreeMap();

  public static GlobalConfig load(String url) {
    try {
      String config = IOUtils.toString(new URL(url));
      return new Gson().fromJson(config, GlobalConfig.class);
    } catch (IOException e) {
      ModList.logger.error("Error downloading the global config.", e);
    }
    return new GlobalConfig();
  }
}
