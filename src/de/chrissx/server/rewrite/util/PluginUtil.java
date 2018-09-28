package de.chrissx.server.rewrite.util;

import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import de.chrissx.server.rewrite.blockedit.BlockEdit;
import de.chrissx.server.rewrite.main.Main;

public class PluginUtil {

	public static void registerCommand(String cmd, CommandExecutor exec) {
		getPlugin().getCommand(cmd).setExecutor(exec);
	}
	
	public static void registerEvents(Listener l) {
		getServer().getPluginManager().registerEvents(l, getPlugin());
	}
	
	public static final Main getPlugin() {
		return Main.getPlugin(Main.class);
	}
	
	public static final Server getServer() {
		return getPlugin().getServer();
	}
	
	public static final BlockEdit getBlockEdit() {
		return getPlugin().getBlockEdit();
	}
}