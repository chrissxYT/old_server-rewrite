package de.chrissx.server.rewrite.util;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class ChatUtil {

	public static void error(CommandSender s, String error) {
		send(s, ChatColor.RED+error);
	}
	
	public static void info(CommandSender s, String info) {
		send(s, ChatColor.GOLD+info);
	}
	
	public static void warn(CommandSender s, String warning) {
		send(s, ChatColor.YELLOW+warning);
	}
	
	private static void send(CommandSender s, String send) {
		s.sendMessage(send);
	}
}