package com.hsun324.simplebukkit.player;

import java.util.List;

import org.bukkit.entity.Player;

import com.hsun324.simplebukkit.SimpleBukkitPlugin;
import com.hsun324.simplebukkit.util.BukkitUtils;

public class ColoredMessageSender
{
	/**
	 * Formats the string with colors.
	 * @param msg input string
	 * @return colored message
	 */
	public static String formatMessage(String msg)
	{
		return msg.replaceAll("&([0-9A-Fa-f])", "\u00A7$1");
	}
	/**
	 * Sends an colored message.
	 * @param player receiver of message
	 * @param msg message to send
	 */
	private static void sendMessage(Player player, String msg)
	{
		player.sendMessage(formatMessage(msg));
	}
	/**
	 * Sends an info (Yellow) message.
	 * @param player receiver of message
	 * @param msg message to send
	 */
	public static void sendInfoMessage(Player player, String msg)
	{
		sendMessage(player, "&e" + msg);
	}
	/**
	 * Sends an warning (Gold) message.
	 * @param player receiver of message
	 * @param msg message to send
	 */
	public static void sendWarningMessage(Player player, String msg)
	{
		sendMessage(player, "&6" + msg);
	}
	/**
	 * Sends an error (Red) message.
	 * @param player receiver of message
	 * @param msg message to send
	 */
	public static void sendErrorMessage(Player player, String msg)
	{
		sendMessage(player, "&c" + msg);
	}
	/**
	 * Sends an critical (Dark Red) message.
	 * @param player receiver of message
	 * @param msg message to send
	 */
	public static void sendCriticalMessage(Player player, String msg)
	{
		sendMessage(player, "&4" + msg);
	}
	/**
	 * Sends an raw (White) message.
	 * @param player receiver of message
	 * @param msg message to send
	 */
	public static void sendRawMessage(Player player, String msg)
	{
		sendMessage(player, msg);
	}
	/**
	 * Sends an raw (White) message using the lines in a List&lt;String&gt;.
	 * @param player receiver of message
	 * @param msg list of lines of message to send
	 */
	public static void sendRawMessage(Player player, List<String> msg)
	{
		for(String line : msg)
			sendMessage(player, proc(player, line));
	}
	private static String proc(Player player, String line)
	{
		return line.replace("%p", player.getName()).replace("%w", player.getWorld().getName()).replace("%t", BukkitUtils.getWorldTimeString(player.getWorld()));
	}
	/**
	 * Sends a message to the whole server.
	 * @param msg message to send
	 */
	public static void announceMessage(String msg)
	{
		SimpleBukkitPlugin.getInstance().getServer().broadcastMessage(formatMessage(msg));
	}
}
