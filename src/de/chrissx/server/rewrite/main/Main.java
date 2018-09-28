package de.chrissx.server.rewrite.main;

import org.bukkit.plugin.java.JavaPlugin;

import de.chrissx.server.rewrite.blockedit.BlockEdit;

public class Main extends JavaPlugin {

	private BlockEdit blockEdit;
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public BlockEdit getBlockEdit() {
		return blockEdit;
	}
}