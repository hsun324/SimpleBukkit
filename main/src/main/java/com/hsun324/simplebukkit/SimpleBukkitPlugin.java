package com.hsun324.simplebukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.hsun324.simplebukkit.bindings.EventBindings;
import com.hsun324.simplebukkit.bindings.event.CommandCaptureEvent;
import com.hsun324.simplebukkit.permissions.PermissionsHandler;

public class SimpleBukkitPlugin extends JavaPlugin
{
	private static SimpleBukkitPlugin instance;
	public static SimpleBukkitPlugin getInstance()
	{
		return instance;
	}
	
	private Logger logger = Logger.getLogger("Minecraft.SimpleBukkit");
	private String version = "";
	
	public Logger getLogger()
	{
		return logger;
	}
	public String getVersion()
	{
		return version;
	}
	public PluginManager getPM()
	{
		return getServer().getPluginManager();
	}
	public void onDisable()
	{
		logger.info("SimpleBukkit v" + getVersion() + ": Disabled");
	}
	public void onEnable()
	{
		SimpleBukkitPlugin.instance = this;
		
		version = getDescription().getVersion();
		
		for(Priority priority : Priority.values())
			EventBindings.getInstance().createListeners(this, getServer().getPluginManager(), priority);
		EventBindings.getInstance().addEventClass(CommandCaptureEvent.class);
		
		PermissionsHandler.getInstance().loadPermissions();
		
		logger.info("SimpleBukkit v" + getVersion() + ": Enabled");
	}
	public Player[] getPlayersInWorld(World world)
	{
		List<Player> playerList = new ArrayList<Player>();
		for(Player player : getServer().getOnlinePlayers())
			if(player.getWorld().equals(world))
				playerList.add(player);
		return playerList.toArray(new Player[0]);
	}
}
