package de.chrissx.server.rewrite.util;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerUtil {

	public static void setMaxHealth(Player p, double health) {
		boolean b = false;
		if(p.getHealth() == p.getMaxHealth()) {
			b = true;
		}
		setMaxHealth(p, health, b);
	}
	
	public static void setMaxHealth(Player p, double hp, boolean setHealth) {
		p.setMaxHealth(hp);
		p.setHealthScaled(false);
		if(setHealth) {
			p.setHealth(hp);
		}
	}
	
	public static int getPing(Player p) {
		return ((CraftPlayer) p).getHandle().ping;
	}
	
	public static String getIp(Player p) {
		return ((CraftPlayer)p).getAddress().getAddress().getHostAddress();
	}
}