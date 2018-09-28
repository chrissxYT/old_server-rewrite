package de.chrissx.server.rewrite.blocklog;

import java.util.List;
import java.util.Stack;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class BlockLogBlock {

	private Stack<BlockChangeEvent> events = new Stack<BlockChangeEvent>();
	private Location loc = new Location(null, 0, 0, 0);
	
	public List<BlockChangeEvent> getEvents() {
		return events;
	}
	
	public void addEvent(BlockChangeEvent event) {
		events.add(event);
	}
	
	public void setEvents(Stack<BlockChangeEvent> events) {
		this.events = events;
	}
	
	public Location getLoc() {
		return loc;
	}
	
	public void setX(String x) {
		loc.setX(Double.parseDouble(x));
	}
	
	public void setY(String y) {
		loc.setY(Double.parseDouble(y));
	}
	
	public void setZ(String z) {
		loc.setZ(Double.parseDouble(z));
	}
	
	public void setWorld(String world) {
		loc.setWorld(Bukkit.getWorld(world));
	}
	
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	
	@Override
	public String toString() {
		return "de.chrissx.server.rewrite.blocklog.BlockLogBlock : DO NOT USE TOSTRING";
	}
}