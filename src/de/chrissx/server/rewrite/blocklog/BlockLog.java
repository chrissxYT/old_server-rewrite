package de.chrissx.server.rewrite.blocklog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.chrissx.server.rewrite.blockedit.BlockEditListener;
import de.chrissx.server.rewrite.blockedit.PosSetEvent;
import de.chrissx.server.rewrite.blockedit.ReplaceEvent;
import de.chrissx.server.rewrite.blockedit.SetEvent;
import de.chrissx.server.rewrite.util.FileUtil;
import de.chrissx.server.rewrite.util.PluginUtil;

public class BlockLog implements Listener, BlockEditListener {

	private File temp_path;
	private File zip_file;
	private List<BlockLogWorld> worlds = new ArrayList<BlockLogWorld>();
	
	public BlockLog(File temp_path, File zip_file) throws IOException {
		PluginUtil.registerEvents(this);
		for(World w : Bukkit.getWorlds())
			worlds.add(new BlockLogWorld(w.getName()));
		this.temp_path = temp_path;
		this.zip_file = zip_file;
		if(zip_file.exists()) {
			FileUtil.unzipFile(zip_file, temp_path);
			for(File w : temp_path.listFiles()) {
				for(File x : w.listFiles()) {
					for(File y : x.listFiles()) {
						for(File z : y.listFiles()) {
							Location l = new Location(Bukkit.getWorld(w.getName()), Integer.parseInt(x.getName()), Integer.parseInt(y.getName()), Integer.parseInt(z.getName()));
							log.put(l, parseFile(z));
						}
					}
				}
			}
			FileUtil.deleteFiles(temp_path);
		}
		PluginUtil.getBlockEdit().addListener(this);
	}
	
	public void onDisable() throws IOException {
		for(Entry<Location, List<BlockChangeEvent>> entry : log.entrySet()) {
			Location l = entry.getKey();
			List<String> save = new ArrayList<String>();
			for(BlockChangeEvent e : entry.getValue()) {
				save.add(e.toString());
			}
			File yPath = Paths.get(temp_path.toString(), l.getWorld().getName(), Integer.toString(l.getBlockX()), Integer.toString(l.getBlockY())).toFile();
			File zFile = Paths.get(yPath.toString(), Integer.toString(l.getBlockZ())).toFile();
			if(!yPath.exists()) {
				yPath.mkdirs();
			}
			FileUtil.write(zFile, save, false);
		}
		FileUtil.zipFiles(temp_path.listFiles(), zip_file);
		FileUtil.deleteFiles(temp_path);
	}
	
	@SuppressWarnings("deprecation")
	private List<BlockChangeEvent> parseFile(File f) throws IOException {
		List<BlockChangeEvent> out = new ArrayList<BlockChangeEvent>();
		List<String> file = FileUtil.read(f);
		for(String s : file) {
			out.add(BlockChangeEvent.parse(s));
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	private List<BlockLogBlock[][][]> parseBlocks(File uncompressedFile) throws FileNotFoundException, XMLStreamException {
		List<BlockLogBlock[][][]> out = new ArrayList<BlockLogBlock[][][]>();
		out.add(new BlockLogBlock[Integer.MAX_VALUE][255][Integer.MAX_VALUE]);//+x +z
		out.add(new BlockLogBlock[Integer.MAX_VALUE][255][Integer.MAX_VALUE]);//-x +z
		out.add(new BlockLogBlock[Integer.MAX_VALUE][255][Integer.MAX_VALUE]);//+x -z
		out.add(new BlockLogBlock[Integer.MAX_VALUE][255][Integer.MAX_VALUE]);//-x -z
		XMLInputFactory in = XMLInputFactory.newInstance();
		InputStream inStr = new FileInputStream(uncompressedFile);
		XMLEventReader read = in.createXMLEventReader(inStr);
		BlockLogBlock currBLB = null;
		while(read.hasNext()) {
			XMLEvent event = read.nextEvent();
			if(event.isStartElement()) {
				StartElement start = event.asStartElement();
				if(start.getName().getLocalPart().equalsIgnoreCase("blocklogblock")) {
					currBLB = new BlockLogBlock();
					Iterator<Attribute> i = start.getAttributes();
					while(i.hasNext()) {
						Attribute a = (Attribute) i.next();
						if(a.getName().getLocalPart().equalsIgnoreCase("world")) {
							currBLB.setWorld(a.getValue());
						}else if(a.getName().getLocalPart().equalsIgnoreCase("x")) {
							currBLB.setX(a.getValue());
						}else if(a.getName().getLocalPart().equalsIgnoreCase("y")) {
							currBLB.setY(a.getValue());
						}else if(a.getName().getLocalPart().equalsIgnoreCase("z")) {
							currBLB.setZ(a.getValue());
						}
					}
				}
				
				if(start.getName().getLocalPart().equalsIgnoreCase("event")) {
					BlockChangeEvent e = new BlockChangeEvent();
					event = read.nextEvent();
					while(!event.isEndElement() && !event.asEndElement().getName().getLocalPart().equalsIgnoreCase("event")) {
						if(event.isStartElement()) {
							StartElement startE = event.asStartElement();
							if(startE.getName().getLocalPart().equalsIgnoreCase("material")) {
								event = read.nextEvent();
								e.setMaterial(Material.getMaterial(event.asCharacters().getData().toUpperCase()));
							}
							if(startE.getName().getLocalPart().equalsIgnoreCase("player")) {
								event = read.nextEvent();
								e.setPlayer(UUID.fromString(event.asCharacters().getData()));
							}
							if(startE.getName().getLocalPart().equalsIgnoreCase("date")) {
								event = read.nextEvent();
								e.setDate(Long.parseLong(event.asCharacters().getData()));
							}
						}
						event = read.nextEvent();
					}
					currBLB.addEvent(e);
					continue;
				}
			}
			
			if(event.isEndElement()) {
				EndElement end = event.asEndElement();
				if(end.getName().getLocalPart().equalsIgnoreCase("blocklogblock")) {
					out.add(currBLB);
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		blockChange(e.getBlock().getLocation(), Material.AIR, e.getPlayer());
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		blockChange(e.getBlockPlaced().getLocation(), e.getBlockPlaced().getType(), e.getPlayer());
	}
	
	public void blockChange(Location l, Material m, Player p) {
		if(log.containsKey(l)) {
			List<BlockChangeEvent> list = log.get(l);
			list.add(new BlockChangeEvent(m, p));
			log.remove(l);
			log.put(l, list);
		}else {
			List<BlockChangeEvent> list = new ArrayList<BlockChangeEvent>();
			list.add(new BlockChangeEvent(m, p));
			log.put(l, list);
		}
	}

	@Override
	public void onPosSet(PosSetEvent event) {}

	@Override
	public void onSetCommand(SetEvent event) {
		for(Block b : event.getBlocks()) {
			blockChange(b.getLocation(), event.getMaterial(), event.getPlayer());
		}
	}

	@Override
	public void onReplaceCommand(ReplaceEvent event) {
		for(Block b : event.getBlocks()) {
			blockChange(b.getLocation(), event.getMaterial(), event.getPlayer());
		}
	}
}