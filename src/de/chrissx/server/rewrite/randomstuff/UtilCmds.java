package de.chrissx.server.rewrite.randomstuff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.chrissx.server.rewrite.util.ChatUtil;
import de.chrissx.server.rewrite.util.CommandUtil;
import de.chrissx.server.rewrite.util.PlayerUtil;
import de.chrissx.server.rewrite.util.PluginUtil;

public class UtilCmds implements CommandExecutor {

	public UtilCmds() {
		PluginUtil.registerCommand("hp", this);
		PluginUtil.registerCommand("ip", this);
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String sidofjunbvunbuuppv, String[] args) {
		if(c.getName().equalsIgnoreCase("hp")) {
			if(!CommandUtil.checkCommand(s, args, false, true, 0, 1))
				return true;
			if(!(s instanceof Player) && args.length == 0) {
				ChatUtil.error(s, "Please specify a player!");
				return true;
			}
			Player p;
			if(args.length == 0) {
				p = (Player)s;
			}else {
				p = Bukkit.getPlayer(args[0]);
			}
			try {
				ChatUtil.info(s, p.getDisplayName()+"'s ping: "+PlayerUtil.getPing(p));
			} catch (Exception e) {
				ChatUtil.error(s, e.getMessage());
			}
			return true;
		}else if(c.getName().equalsIgnoreCase("ip")) {
			if(!CommandUtil.checkCommand(s, args, false, true, 0, 1))
				return true;
			if(!(s instanceof Player) && args.length == 0) {
				ChatUtil.error(s, "Please specify a player!");
				return true;
			}
			Player p;
			if(args.length == 0) {
				p = (Player)s;
			}else {
				p = Bukkit.getPlayer(args[0]);
			}
			try {
				ChatUtil.info(s, p.getDisplayName()+"'s ip: "+PlayerUtil.getIp(p));
			} catch (Exception e) {
				ChatUtil.error(s, e.getMessage());
			}
			return true;
		}else if(c.getName().equalsIgnoreCase("sudo")) {
			if(!CommandUtil.checkCommand(s, args, false, true, 2, Integer.MAX_VALUE))
				return true;
			Player p = Bukkit.getPlayer(args[0]);
			String msg = "";
			for(int i = 1; i < args.length; i++)
				msg+=" "+args[i];
			try {
				p.chat(msg);
			} catch (Exception e) {
				ChatUtil.error(s, e.getMessage());
			}
		}
		return false;
	}

}