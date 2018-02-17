package net.soartex.modlistbuilder.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraft.util.text.TextFormatting.YELLOW;

public class MLBCommand extends CommandBase {
  @Nonnull
  @Override
  public String getName() {
    return "mlb";
  }

  @Nonnull
  @Override
  public String getUsage(@Nonnull ICommandSender p_71518_1_) {
    return "mlb help";
  }

  @Nonnull
  @Override
  public List getAliases() {
    return new ArrayList();
  }

  @Override
  public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
    if (args[0].equals("help")) {
      ITextComponent chat = new TextComponentString("You can find help for making a modpack json by visiting the ");
      Style url = new Style();
      url.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://wiki.soartex.net/modded/02-04-2015/making-a-modpack-json/"));
      chat.appendSibling(new TextComponentString(YELLOW + "wiki").setStyle(url));
      sender.sendMessage(chat);
    }
  }

  @Override
  public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
    return true;
  }

  @Nonnull
  @Override
  public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
    return Collections.singletonList("help");
  }


  @Override
  public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
    return false;
  }
}
