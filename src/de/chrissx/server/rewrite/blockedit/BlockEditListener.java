package de.chrissx.server.rewrite.blockedit;

public interface BlockEditListener {

	public void onPosSet(PosSetEvent event);
	
	public void onSetCommand(SetEvent event);
	
	public void onReplaceCommand(ReplaceEvent event);
}