package de.chrissx.server.rewrite.chatlog;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.chrissx.server.rewrite.util.FileUtil;
import de.chrissx.server.rewrite.util.PluginUtil;

public class ChatLog implements Listener {

	private volatile List<String> log = new ArrayList<String>();
	private String path;
	private String ext;
	
	public ChatLog(String path, String ext) {
		PluginUtil.registerEvents(this);
		this.path = path;
		this.ext = ext;
	}
	
	public void onDisable() throws IOException {
		FileUtil.write(Paths.get(path, System.currentTimeMillis()+ext).toFile(), log, false);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		log.add(System.currentTimeMillis()+" : "+e.getPlayer().getName()+" : "+e.getMessage());
	}
}