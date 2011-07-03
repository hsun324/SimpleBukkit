package com.hsun324.simplebukkit.permissions;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.hsun324.simplebukkit.SimpleBukkitPlugin;
import com.nijiko.permissions.Group;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class PermissionsHandler
{
	private final static PermissionsHandler instance = new PermissionsHandler();
	public static PermissionsHandler getInstance()
	{
		return instance;
	}
	
	private PermissionHandler permissionHandler;
	
	public void loadPermissions()
	{
		Plugin permissionsCandidate = SimpleBukkitPlugin.getInstance().getPM().getPlugin("Permissions");
		if(permissionsCandidate != null && permissionsCandidate instanceof Permissions)
		{
			permissionHandler = ((Permissions) permissionsCandidate).getHandler();
			SimpleBukkitPlugin.getInstance().getLogger().info("SimpleCommands: Permissions (Phoenix) was found.");
		}
		else
			SimpleBukkitPlugin.getInstance().getLogger().warning("SimpleCommands: Permissions (Phoenix) was not found. Using OP for permissions.");
	}
	
	public boolean hasPermission(Player player, String permission)
	{
		if(permissionHandler != null)
			return permissionHandler.has(player, permission);
		return player.isOp();
	}
	
	public boolean isInGroup(Player player, String group)
	{
		if(permissionHandler != null)
			return permissionHandler.inGroup(player.getWorld().getName(), player.getName(), group);
		return false;
	}
	
	
	public String getGroup(Player player)
	{
		if(permissionHandler != null)
		{
			String[] groups = permissionHandler.getGroups(player.getWorld().getName(), player.getName());
			if(groups.length > 0)
				return groups[0];
			Group defaultGroup = permissionHandler.getDefaultGroup(player.getWorld().getName());
			if(defaultGroup != null)
				return defaultGroup.getName();
		}
		return "";
	}
	
	public String getPrefix(Player player)
	{
		if(permissionHandler != null)
			return permissionHandler.getUserPrefix(player.getWorld().getName(), player.getName());
		return "";
	}
	
	public String getSuffix(Player player)
	{
		if(permissionHandler != null)
			return permissionHandler.getUserSuffix(player.getWorld().getName(), getGroup(player));
		return "";
	}

	public boolean isPermissionsLoaded()
	{
		return permissionHandler != null;
	}
}
