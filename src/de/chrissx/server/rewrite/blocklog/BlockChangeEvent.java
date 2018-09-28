package de.chrissx.server.rewrite.blocklog;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BlockChangeEvent {

	private Material m = null;
	private UUID p = null;
	private long d = -1;
	
	public Material getMaterial() {
		return m;
	}

	public void setMaterial(Material m) {
		this.m = m;
	}

	public UUID getPlayer() {
		return p;
	}

	public void setPlayer(UUID p) {
		this.p = p;
	}

	public long getDate() {
		return d;
	}

	public void setDate(long d) {
		this.d = d;
	}

	public BlockChangeEvent() {
		
	}
	
	public BlockChangeEvent(Material m, Player p) {
		this.m = m;
		this.p = p.getUniqueId();
		this.d = System.currentTimeMillis();
	}
	
	public BlockChangeEvent(Material m, UUID p, long d) {
		this.m = m;
		this.p = p;
		this.d = d;
	}
	
	@Deprecated
	public static BlockChangeEvent parse(String s) {
		String[] split = s.split(" ");
		return new BlockChangeEvent(Material.getMaterial(split[1]), UUID.fromString(split[0]), Long.parseLong(split[2]));
	}
	
	@Override
	public String toString() {
		return p+" "+m+" "+d;
	}
}