package de.chrissx.server.rewrite.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUtil {

	/**
	 * 
	 * @param s The CommandSender that sent the command.
	 * @param args The args given from the CommandSender.
	 * @param mustBePlayer Must the sender be a player?
	 * @param mustBeOp Must the sender be op?
	 * @param minArgs Minimum number of args. (Checked with args.length)
	 * @param maxArgs Maximum number of args. (Checked with args.length)
	 * @return true if the sender can execute the command, false if the sender cannot execute the command
	 */
	public static boolean checkCommand(CommandSender s, String[] args, boolean mustBePlayer, boolean mustBeOp, int minArgs, int maxArgs) {
		if(mustBePlayer && !(s instanceof Player)) {
			ChatUtil.error(s, "You are not a player.");
			return false;
		}
		if(mustBeOp && s instanceof Player && !((Player)s).isOp()) {
			ChatUtil.error(s, "You are not op.");
			return false;
		}
		if(args.length < minArgs) {
			ChatUtil.error(s, "Not enough args.");
			return false;
		}
		if(args.length > maxArgs) {
			ChatUtil.error(s, "Too many args.");
			return false;
		}
		if(args.length >= minArgs && args.length <= maxArgs && mustBeOp ? s instanceof Player ? ((Player)s).isOp() : true : true && mustBePlayer ? s instanceof Player : true) {
			return true;
		}else {
			ChatUtil.error(s, "ERROR IN CHRISSX' COMMAND-CHECKER, PLEASE REPORT WITH YOUR FULL COMMAND(de.chrissx.server.rewrite.util.CommandUtil:38)");
			return false;
		}
	}
}