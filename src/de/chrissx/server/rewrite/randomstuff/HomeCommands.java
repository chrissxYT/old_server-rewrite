package de.chrissx.server.rewrite.randomstuff;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.chrissx.server.rewrite.util.ChatUtil;
import de.chrissx.server.rewrite.util.CommandUtil;
import de.chrissx.server.rewrite.util.FileUtil;
import de.chrissx.server.rewrite.util.PluginUtil;

public class HomeCommands implements CommandExecutor {

	private Map<UUID, List<PlayerHome>> homes = new HashMap<UUID, List<PlayerHome>>();
	private Path temp_path;
	private File config_file;
	private String ext;
	
	public HomeCommands(Path temp_path, File config_file, String ext) throws IOException {
		this.temp_path = temp_path;
		this.config_file = config_file;
		this.ext = ext;
		PluginUtil.registerCommand("sethome", this);
		PluginUtil.registerCommand("home", this);
		if(config_file.exists()) {
			FileUtil.unzipFile(config_file, temp_path.toFile());
			for(File f : temp_path.toFile().listFiles()) {
				UUID uuid = UUID.fromString(f.getName().replaceAll(ext, ""));
				List<PlayerHome> hms = new ArrayList<PlayerHome>();
				for(String s : FileUtil.read(f)) {
					hms.add(PlayerHome.parse(s));
				}
				homes.put(uuid, hms);
			}
			FileUtil.deleteFiles(temp_path.toFile());
		}
	}
	
	public void onDisable() throws IOException {
		for(Entry<UUID, List<PlayerHome>> entry : homes.entrySet()) {
			File f = Paths.get(temp_path.toString(), entry.getKey().toString()+ext).toFile();
			List<String> write = new ArrayList<String>();
			for(PlayerHome h : entry.getValue()) {
				write.add(h.toString());
			}
			FileUtil.write(f, write, false);
		}
		FileUtil.zipFiles(temp_path.toFile().listFiles(), config_file);
		FileUtil.deleteFiles(temp_path.toFile());
	}
	
	private int getIndex(List<PlayerHome> hms, String name) {
		for(int i = 0; i < hms.size(); i++) {
			if(hms.get(i).getName().equalsIgnoreCase(name))
				return i;
		}
		throw new HomeNotFoundException("Home not found.");
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String ichhängemitgrossstadtkidsausdempotwiepokerchips, String[] args) {
		if(c.getName().equalsIgnoreCase("sethome")) {
			if(!CommandUtil.checkCommand(s, args, true, false, 0, 1))
				return true;
			Player p = (Player)s;
			UUID u = p.getUniqueId();
			if(homes.containsKey(u)) {
				List<PlayerHome> homeList = homes.get(u);
				if(args.length == 0) {
					PlayerHome h = homeList.get(0);
					h.setLocation(p.getLocation());
					homeList.set(0, h);
				}else {
					try {
						int i = getIndex(homeList, args[0]);
						PlayerHome h = homeList.get(i);
						h.setLocation(p.getLocation());
						homeList.set(i, h);
					}catch(Exception e) {
						homeList.add(new PlayerHome(p.getLocation(), args[0]));
					}
				}
				homes.remove(u);
				homes.put(u, homeList);
			}else {
				List<PlayerHome> hms = new ArrayList<PlayerHome>();
				hms.add(new PlayerHome(p.getLocation(), "home"));
				homes.put(u, hms);
			}
			ChatUtil.info(p, "Home set.");
			return true;
		}else if(c.getName().equalsIgnoreCase("home")) {
			if(!CommandUtil.checkCommand(s, args, true, false, 0, 1))
				return true;
			Player p = (Player)s;
			UUID u = p.getUniqueId();
			if(!homes.containsKey(u)) {
				ChatUtil.error(p, "You don't have any homes.");
				return true;
			}
			PlayerHome h;
			if(args.length == 0)
				h = homes.get(u).get(0);
			else
				try {
					h = homes.get(u).get(getIndex(homes.get(u), args[0]));
				} catch (Exception e) {
					ChatUtil.error(s, e.getMessage());
					return true;
				}
			h.tp(p);
			return true;
		}
		return false;
	}
}