package de.chrissx.server.rewrite.blockedit;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ReplaceEvent extends BlockEditEvent {

	private Material m;
	private List<Block> blocks;
	
	public ReplaceEvent(Player player, Location loc, Material m, List<Block> blocks) {
		super(player, loc);
		this.m = m;
		this.blocks = blocks;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public Material getMaterial() {
		return m;
	}
}