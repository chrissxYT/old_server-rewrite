package de.chrissx.server.rewrite.blocklog;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.bukkit.Location;

public class BlockLogWorld {

	private String name;
	private BlockLogBlock[][][] XPosZPosLog = new BlockLogBlock[Integer.MAX_VALUE][255][Integer.MAX_VALUE];//+x +z
	private BlockLogBlock[][][] XNegZPosLog = new BlockLogBlock[Integer.MAX_VALUE][255][Integer.MAX_VALUE];//-x +z
	private BlockLogBlock[][][] XPosZNegLog = new BlockLogBlock[Integer.MAX_VALUE][255][Integer.MAX_VALUE];//+x -z
	private BlockLogBlock[][][] XNegZNegLog = new BlockLogBlock[Integer.MAX_VALUE][255][Integer.MAX_VALUE];//-x -z
	private BlockLogBlock zeroZeroZero = new BlockLogBlock();
	
	public BlockLogWorld(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	/*
	 * Gets a list of the BlockLogBlocks in the following format:
	 * 
	 * 0 - XPosZPos                                        +x +z
	 * 1 - XNegZPos                                        -x +z
	 * 2 - XPosZNeg                                        +x -z
	 * 3 - XNegZNeg                                        -x -z
	 */
	public List<BlockLogBlock[][][]> getBlocks() {
		return new ArrayList<BlockLogBlock[][][]>() {
			private static final long serialVersionUID = -2481316717351801722L;
		{
			add(XPosZPosLog);
			add(XNegZPosLog);
			add(XPosZNegLog);
			add(XNegZNegLog);
		}};
	}
	
	public void addEvent(Location loc, BlockChangeEvent e) {
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		if(x > 0 && z > 0)
			addEvent(x, y, z, e, XPosZPosLog);
		else if(x > 0 && z < 0)
			addEvent(x, y, -z, e, XPosZNegLog);
		else if(x < 0 && z > 0)
			addEvent(-x, y, z, e, XNegZPosLog);
		else if(x < 0 && z < 0)
			addEvent(-x, y, -z, e, XNegZNegLog);
		else
			zeroZeroZero.addEvent(e);
	}
	
	private void addEvent(int x, int y, int z, BlockChangeEvent e, BlockLogBlock[][][] array) {
		if(array[x][y][z] == null)
			array[x][y][z] = new BlockLogBlock();
		array[x][y][z].addEvent(e);
	}
	
	public void setBlockChanges(Location loc, Stack<BlockChangeEvent> events) {
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		if(x > 0 && z > 0)
			setEvents(x, y, z, events, XPosZPosLog);
		else if(x > 0 && z < 0)
			setEvents(x, y, -z, events, XPosZNegLog);
		else if(x < 0 && z > 0)
			setEvents(-x, y, z, events, XNegZPosLog);
		else if(x < 0 && z < 0)
			setEvents(-x, y, -z, events, XNegZNegLog);
		else
			zeroZeroZero.setEvents(events);
	}
	
	private void setEvents(int x, int y, int z, Stack<BlockChangeEvent> events, BlockLogBlock[][][] blockArray) {
		if(blockArray[x][y][z] == null)
			blockArray[x][y][z] = new BlockLogBlock();
		blockArray[x][y][z].setEvents(events);
	}
}
