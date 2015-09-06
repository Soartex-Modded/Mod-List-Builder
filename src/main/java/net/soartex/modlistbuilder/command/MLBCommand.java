package net.soartex.modlistbuilder.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MLBCommand implements ICommand {
  @Override
  public String getCommandName() {
    return "mlb";
  }

  @Override
  public String getCommandUsage(ICommandSender p_71518_1_) {
    return "mlb help";
  }

  @Override
  public List getCommandAliases() {
    return new ArrayList();
  }

  @Override
  public void processCommand(ICommandSender sender, String[] args) {
    if (args[0].equals("help")) {
      IChatComponent chat = new ChatComponentText("You can find help for making a modpack json by visiting the ");
      ChatStyle url = new ChatStyle();
      url.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://wiki.soartex.net/modded/02-04-2015/making-a-modpack-json/"));
      chat.appendSibling(new ChatComponentText(EnumChatFormatting.YELLOW + "wiki").setChatStyle(url));
      sender.addChatMessage(chat);
    }
  }

  @Override
  public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
    return true;
  }

  @Override
  public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
    return Collections.singletonList("help");
  }

  @Override
  public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
    return false;
  }

  @Override
  public int compareTo(Object o) {
    return 0;
  }
}
