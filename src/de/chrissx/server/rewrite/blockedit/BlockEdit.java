package de.chrissx.server.rewrite.blockedit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.chrissx.server.rewrite.util.PluginUtil;

public class BlockEdit implements CommandExecutor, Listener {

	private HashMap<Player, Location[]> positions = new HashMap<Player, Location[]>(PluginUtil.getServer().getMaxPlayers());
	private String[] cmds = new String[] {"pos1","pos2","replace","set","biome"};
	private List<BlockEditListener> listeners = new ArrayList<BlockEditListener>();
	
	public BlockEdit() {
		for(String s : cmds) {
			PluginUtil.registerCommand(s, this);
		}
		PluginUtil.registerEvents(this);
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String label_stuff_useless_af, String[] args) {
		if(c.getName().equalsIgnoreCase("pos1")) {
			
		}else if(c.getName().equalsIgnoreCase("pos2")) {
			
		}else if(c.getName().equalsIgnoreCase("replace")) {
			
		}else if(c.getName().equalsIgnoreCase("set")) {
			
		}else if(c.getName().equalsIgnoreCase("biome")) {
			
		}
		return false;
	}
	
	public void setPos1(Player p, Location l) {
		if(positions.containsKey(p)) {
			Location[] poss = positions.get(p);
			poss[0] = l;
			positions.remove(p);
			positions.put(p, poss);
		}else {
			positions.put(p, new Location[] {l, null});
		}
	}
	
	public void setPos2(Player p, Location l) {
		if(positions.containsKey(p)) {
			Location[] poss = positions.get(p);
			poss[1] = l;
			positions.remove(p);
			positions.put(p, poss);
		}else {
			positions.put(p, new Location[] {null, l});
		}
	}
	
	public void addListener(BlockEditListener listener) {
		listeners.add(listener);
	}
}