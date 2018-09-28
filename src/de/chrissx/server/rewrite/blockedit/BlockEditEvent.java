package de.chrissx.server.rewrite.blockedit;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BlockEditEvent {
	
	protected Player player;
	protected Location loc;
	
	public BlockEditEvent(Player player, Location loc) {
		this.player = player;
		this.loc = loc;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public Player getPlayer() {
		return player;
	}
}