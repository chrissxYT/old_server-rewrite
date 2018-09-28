package de.chrissx.server.rewrite.randomstuff;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerHome {

	private String name;
	private Location loc;
	
	public PlayerHome(Location loc, String name) {
		this.loc = loc;
		this.name = name;
	}
	
	public void tp(Player p) {
		p.teleport(loc);
	}
	
	public static PlayerHome parse(String line) {
		String[] s = line.split(" ");
		return new PlayerHome(new Location(Bukkit.getWorld(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]), Integer.parseInt(s[4])), s[0]);
	}
	
	@Override
	public String toString() {
		return name+" "+loc.getWorld().getName()+" "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ();
	}
	
	public String getName() {
		return name;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public void setLocation(Location l) {
		loc = l;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}