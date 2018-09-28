package de.chrissx.server.rewrite.blockedit;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PosSetEvent extends BlockEditEvent {
	
	private byte pos;
	
	public PosSetEvent(Player player, Location loc, byte pos) {
		super(player, loc);
		this.pos = pos;
	}
	
	public byte getPositionId() {
		return pos;
	}
}