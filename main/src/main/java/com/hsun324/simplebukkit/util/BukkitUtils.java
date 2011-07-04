package com.hsun324.simplebukkit.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.hsun324.simplebukkit.SimpleBukkitPlugin;
import com.hsun324.simplebukkit.api.enums.WeatherType;
import com.hsun324.simplebukkit.permissions.PermissionsHandler;
import com.hsun324.simplebukkit.worldemu.ItemDropInfo;

public final class BukkitUtils
{

	public static void simulateBlockMine(Block block)
	{
		Location blockLocation = block.getLocation();
		Material type = block.getType();
		byte data = block.getData();
		
	    if (type == Material.STONE)
	    	dropItem(blockLocation, Material.COBBLESTONE);
	    else if (type == Material.GRASS)
	    	dropItem(blockLocation, Material.DIRT);
	    else if (type == Material.BEDROCK) { }
	    else if (type == Material.WATER) { }
	    else if (type == Material.STATIONARY_WATER) { }
	    else if (type == Material.LAVA) { }
	    else if (type == Material.STATIONARY_LAVA) { }
	    else if (type == Material.GRAVEL)
	    {
	        if (random.nextDouble() >= 0.9)
	            dropItem(blockLocation, Material.FLINT);
	        else
	            dropItem(blockLocation, Material.GRAVEL);
	    }
	    else if (type == Material.COAL_ORE)
	    	dropItem(blockLocation, Material.COAL);
	    else if (type == Material.IRON_ORE)
	    	dropItem(blockLocation, Material.IRON_INGOT);
	    else if (type == Material.GOLD_ORE)
	    	dropItem(blockLocation, Material.GOLD_INGOT);
	    else if (type == Material.LEAVES)
	    {
	        if (random.nextDouble() > 0.95)
	            dropItem(blockLocation, Material.SAPLING);
	    }
	    else if (type == Material.LAPIS_ORE)
	    	dropItem(blockLocation, Material.INK_SACK, (random.nextInt(5)+4), (byte) 4);
	    else if (type == Material.BED)
	    	dropItem(blockLocation, Material.BED);
	    else if (type == Material.WOOL)
	    	dropItem(blockLocation, Material.WOOL, 1, data);
	    else if (type == Material.DOUBLE_STEP || type == Material.STEP)
	        dropItem(blockLocation, Material.STEP, 2, data);
	    else if (type == Material.BOOKSHELF)
	        dropItem(blockLocation, Material.BOOKSHELF);
	    else if (type == Material.FIRE) { }
	    else if (type == Material.MOB_SPAWNER) { }
	    else if (type == Material.WOOD_STAIRS)
	    	dropItem(blockLocation, Material.WOOD);
	    else if (type == Material.REDSTONE_WIRE)
	    	dropItem(blockLocation, Material.REDSTONE);
	    else if (type == Material.DIAMOND_ORE)
	    	dropItem(blockLocation, Material.DIAMOND, 1);
	    else if (type == Material.CROPS)
	    {
	    	dropItem(blockLocation, Material.WHEAT, 1);
	    	dropItem(blockLocation, Material.SEEDS, random.nextInt(4));
	    }
	    else if (type == Material.SOIL)
	    	dropItem(blockLocation, Material.DIRT);
	    else if (type == Material.BURNING_FURNACE)
	    	dropItem(blockLocation, Material.FURNACE);
	    else if (type == Material.SIGN_POST)
	    	dropItem(blockLocation, Material.SIGN);
	    else if (type == Material.WOODEN_DOOR)
	    	dropItem(blockLocation, Material.WOOD_DOOR);
	    else if (type == Material.COBBLESTONE_STAIRS)
	    	dropItem(blockLocation, Material.COBBLESTONE);
	    else if (type == Material.WALL_SIGN)
	    	dropItem(blockLocation, Material.SIGN);
	    else if (type == Material.IRON_DOOR_BLOCK)
	    	dropItem(blockLocation, Material.IRON_DOOR);
	    else if (type == Material.REDSTONE_ORE || type == Material.GLOWING_REDSTONE_ORE)
	    	dropItem(blockLocation, Material.REDSTONE, 4+random.nextInt(4));
	    else if (type == Material.REDSTONE_TORCH_OFF)
	    	dropItem(blockLocation, Material.REDSTONE_TORCH_ON);
	    else if (type == Material.SNOW)
	    	dropItem(blockLocation, Material.SNOW_BALL);
	    else if (type == Material.ICE) { }
	    else if (type == Material.CLAY)
	    	dropItem(blockLocation, Material.CLAY_BALL, 4);
	    else if (type == Material.SUGAR_CANE_BLOCK)
	    	dropItem(blockLocation, Material.SUGAR_CANE);
	    else if (type == Material.PORTAL) { }
	    else if (type == Material.CAKE_BLOCK)
	    	dropItem(blockLocation, Material.SUGAR, 2);
	    else if (type == Material.DIODE_BLOCK_ON || type == Material.DIODE_BLOCK_OFF)
	    	dropItem(blockLocation, Material.DIODE);
	    else if (type == Material.LONG_GRASS)
	    {
	        if (random.nextDouble() > 0.95)
	        	dropItem(blockLocation, Material.SEEDS);
	    }
	    else if (type == Material.DEAD_BUSH) { }
	    else if (type == Material.DEAD_BUSH) { }
	    else if (type == Material.DEAD_BUSH) { }
	    else if (type == Material.DEAD_BUSH) { }
	    else if (type != Material.AIR)
	        dropItem(blockLocation, type, 1, (byte) data);
	}

	/**
	 * Drops an specified item at the specified {@link Location}. 
	 * @param place the location to drop the item
	 * @param item the type of item to drop
	 */
	public static void dropItem(Location place, Material item)
	{
		dropItem(place, item, 1, (byte) 0, true);
	}

	/**
	 * Drops an specified amount of an item at the specified {@link Location}. 
	 * @param place the location to drop the item
	 * @param item the type of item to drop
	 * @param times the amount of item to drop
	 */
	public static void dropItem(Location place, Material item, int times)
	{
		dropItem(place, item, times, (byte) 0, true);
	}

	/**
	 * Drops an specified amount of an item at the specified {@link Location}. 
	 * @param place the location to drop the item
	 * @param item the type of item to drop
	 * @param times the amount of item to drop
	 * @param damage the damage value of the item to drop
	 */
	public static void dropItem(Location place, Material item, int times, byte damage)
	{
		dropItem(place, item, times, damage, true);
	}

	/**
	 * Drops an specified amount of an item at the specified {@link Location}. 
	 * @param place the location to drop the item
	 * @param item the type of item to drop
	 * @param times the amount of item to drop
	 * @param damage the damage value of the item to drop
	 * @param scattered whether to scatter the drops or not.
	 */
	public static void dropItem(Location place, Material item, int times, byte damage, boolean scattered)
	{
		if(scattered)
			new ItemDropInfo(item, 100, times, times, damage).drop(place, random);
		else
			place.getWorld().dropItemNaturally(place, new ItemStack(item, times, damage));
	}

	/**
	 * Gets the current weather in the specified world as a {@link WeatherType}.
	 * @param world the world to get the weather from
	 * @return the current weather in the specified world
	 * @deprecated
	 */
	@Deprecated
	public static WeatherType getWorldWeather(World world)
	{
		if(!world.hasStorm())
			return WeatherType.CLEAR;
		if(world.isThundering())
			return WeatherType.THUNDERING;
		return WeatherType.RAINY;
	}

	@Deprecated
	public static void setWorldWeather(World world, WeatherType weather)
	{
		if(weather == WeatherType.CLEAR)
		{
			world.setStorm(false);
			world.setThundering(false);
		}
		else if(weather == WeatherType.RAINY)
		{
			world.setStorm(true);
			world.setThundering(false);
		}
		else if(weather == WeatherType.THUNDERING)
		{
			world.setStorm(true);
			world.setThundering(true);
		}
	}

	/**
	 * Checks whether the specified type of {@link Material} is solid.
	 * @param type the type to check
	 * @return whether the type is solid
	 */
	public static boolean isSolid(Material type)
	{
		if(!isTransparent(type))
			return true;
		if(type == Material.GLASS || 
		   type == Material.DISPENSER || 
		   type == Material.NOTE_BLOCK || 
		   type == Material.BED || 
		   type == Material.STEP || 
		   type == Material.MOB_SPAWNER || 
		   type == Material.WOOD_STAIRS || 
		   type == Material.SOIL || 
		   type == Material.WOODEN_DOOR || 
		   type == Material.COBBLESTONE_STAIRS || 
		   type == Material.IRON_DOOR_BLOCK || 
		   type == Material.ICE || 
		   type == Material.CACTUS || 
		   type == Material.FENCE || 
		   type == Material.CAKE || 
		   type == Material.TRAP_DOOR || 
		   type == Material.DIODE_BLOCK_OFF || 
		   type == Material.DIODE_BLOCK_ON)
			return true;
		return false;
	}

	/**
	 * Checks whether the specified type of {@link Material} is transparent.
	 * @param type the type to check
	 * @return whether the type is transparent
	 */
	public static boolean isTransparent(Material type)
	{
		if(type == Material.AIR || 
		   type == Material.SAPLING || 
		   type == Material.WATER || 
		   type == Material.STATIONARY_WATER || 
		   type == Material.LAVA || 
		   type == Material.STATIONARY_LAVA || 
		   type == Material.GLASS || 
		   type == Material.DISPENSER || 
		   type == Material.NOTE_BLOCK || 
		   type == Material.BED || 
		   type == Material.YELLOW_FLOWER || 
		   type == Material.RED_ROSE || 
		   type == Material.BROWN_MUSHROOM || 
		   type == Material.RED_MUSHROOM || 
		   type == Material.STEP || 
		   type == Material.TORCH || 
		   type == Material.FIRE || 
		   type == Material.MOB_SPAWNER || 
		   type == Material.WOOD_STAIRS || 
		   type == Material.REDSTONE_WIRE || 
		   type == Material.CROPS || 
		   type == Material.SOIL || 
		   type == Material.SIGN_POST || 
		   type == Material.WOODEN_DOOR || 
		   type == Material.LADDER || 
		   type == Material.RAILS || 
		   type == Material.POWERED_RAIL || 
		   type == Material.DETECTOR_RAIL || 
		   type == Material.COBBLESTONE_STAIRS || 
		   type == Material.WALL_SIGN || 
		   type == Material.LEVER || 
		   type == Material.STONE_PLATE || 
		   type == Material.IRON_DOOR_BLOCK || 
		   type == Material.WOOD_PLATE || 
		   type == Material.REDSTONE_TORCH_OFF || 
		   type == Material.REDSTONE_TORCH_ON || 
		   type == Material.STONE_BUTTON || 
		   type == Material.SNOW || 
		   type == Material.ICE || 
		   type == Material.CACTUS || 
		   type == Material.SUGAR_CANE_BLOCK || 
		   type == Material.FENCE || 
		   type == Material.PORTAL || 
		   type == Material.CAKE || 
		   type == Material.TRAP_DOOR || 
		   type == Material.DEAD_BUSH || 
		   type == Material.WEB || 
		   type == Material.LONG_GRASS || 
		   type == Material.DIODE_BLOCK_OFF || 
		   type == Material.DIODE_BLOCK_ON || 
		   type == Material.PISTON_BASE || 
		   type == Material.PISTON_STICKY_BASE || 
		   type == Material.PISTON_EXTENSION || 
		   type == Material.PISTON_MOVING_PIECE)
			return true;
		return false;
	}

	/**
	 * Teleports the specified {@link Player} to the specified {@link Location} safely.
	 * @param player the player to teleport
	 * @param destination the location to teleport the player
	 */
	public static void safeTeleport(Player player, Location destination)
	{
		World teleportWorld = destination.getWorld();
		int lastValidLocation = -1;
		for(int currentY = destination.getBlockY(); currentY < 128; currentY++)
		{
			Block blockAt = teleportWorld.getBlockAt(new Location(destination.getWorld(), destination.getX(), currentY, destination.getZ()));
			Block blockAbove = blockAt.getFace(BlockFace.UP);
			Block blockBelow = blockAt.getFace(BlockFace.DOWN);
			if(isTransparent(blockAt.getType()) && isTransparent(blockAbove.getType()) && isSolid(blockBelow.getType()))
			{
				lastValidLocation = currentY;
				break;
			}
				
		}
		if(lastValidLocation == -1)
		{
			Block blockAt = teleportWorld.getBlockAt(destination);
			Block blockBelow = blockAt.getFace(BlockFace.DOWN);
			Block blockAbove = blockAt.getFace(BlockFace.UP);
			if(!isTransparent(blockAt.getType()))
				blockAt.setType(Material.AIR);
			if(blockAbove.getType() != Material.TORCH)
				blockAbove.setType(Material.TORCH);
			if(!isSolid(blockBelow.getType()))
				blockBelow.setType(Material.STONE);
		}
		else
			destination.setY(lastValidLocation);
		player.teleport(destination);
	}

	/**
	 * Matches a players using the CraftBook system.
	 * @param sourcePlayer the source player
	 * @param filter the filter
	 * @return players matching the filter
	 */
	public static Iterable<Player> getPlayersMatching(Player sourcePlayer, String filter )
	{
	    Player[] players = SimpleBukkitPlugin.getInstance().getServer().getOnlinePlayers();
	    filter = filter.toLowerCase();
	    if (filter.equals("*"))
	        return Arrays.asList(SimpleBukkitPlugin.getInstance().getServer().getOnlinePlayers());
	
	    if (filter.charAt(0) == '#')
	    {
	        if (filter.equalsIgnoreCase("#world"))
	            return Arrays.asList(SimpleBukkitPlugin.getInstance().getPlayersInWorld(sourcePlayer.getWorld()));
	        else if (filter.equalsIgnoreCase("#near"))
	        {
	            List<Player> playersMatchedList = new ArrayList<Player>();
	            World sourceWorld = sourcePlayer.getWorld();
	            Vector sourceVector = sourcePlayer.getLocation().toVector();
	            for (Player player : SimpleBukkitPlugin.getInstance().getServer().getOnlinePlayers())
	                if (player.getWorld().equals(sourceWorld) && player.getLocation().toVector().distanceSquared(sourceVector) < 900)
	                	playersMatchedList.add(player);
	            return playersMatchedList;
	        }
	        return null;
	    }
	    if (filter.charAt(0) == '@' && filter.length() >= 2)
	    {
	        filter = filter.substring(1);
	        for (Player player : players)
	            if (player.getName().equalsIgnoreCase(filter))
	            {
	                List<Player> list = new ArrayList<Player>();
	                list.add(player);
	                return list;
	            }
	        return null;
	    }
	    else if (filter.charAt(0) == '*' && filter.length() >= 2)
	    {
	        filter = filter.substring(1);
	        List<Player> list = new ArrayList<Player>();
	        for (Player player : players)
	            if (player.getName().toLowerCase().contains(filter))
	                list.add(player);
	        return list;
	    }
	    else
	    {
	        List<Player> list = new ArrayList<Player>();
	        for (Player player : players)
	            if (player.getName().toLowerCase().startsWith(filter))
	                list.add(player);
	        return list;
	    }
	}

	public static Material getMaterial(String name)
	{
		Material material = Material.matchMaterial(name);
		if(material == null)
			material = Material.getMaterial(IntegerUtils.getInt(name));
		if(material == null)
			for(Material mat : Material.values())
				if(name.equals(Util.getSimpleEnumName(mat.name())))
					return mat;
		return material;
	}

	public static String getOnlineChars()
	{
		return getOnlineChars(null, true, true, true, false);
	}

	public static String getOnlineChars(Player excludePlayer)
	{
		return getOnlineChars(excludePlayer, true, true, true, false);
	}

	public static String getOnlineChars(Player excludePlayer, boolean addGroup)
	{
		return getOnlineChars(excludePlayer, addGroup, true, true, false);
	}

	public static String getOnlineChars(Player excludePlayer, boolean addGroup, boolean addSuffixAfterName)
	{
		return getOnlineChars(excludePlayer, addGroup, true, true, addSuffixAfterName);
	}

	public static String getOnlineChars(Player excludePlayer, boolean addGroup, boolean addPrefix, boolean addSuffix)
	{
		return getOnlineChars(excludePlayer, addGroup, addPrefix, addSuffix, false);
	}

	private static String getOnlineChars(Player excludePlayer, boolean addGroup, boolean addPrefix, boolean addSuffix, boolean addSuffixAfterName)
	{
		Player[] onlinePlayers = SimpleBukkitPlugin.getInstance().getServer().getOnlinePlayers();
		String message = "";
		PermissionsHandler handler = PermissionsHandler.getInstance();
		for(Player onlinePlayer : onlinePlayers)
		{
			if(excludePlayer == null || !onlinePlayer.getName().equals(excludePlayer.getName()))
			{
				message += ", ";
				if(handler.isPermissionsLoaded())
					message += (addPrefix?handler.getPrefix(onlinePlayer):"") + "[" + handler.getGroup(onlinePlayer) + "]" + " ";
				if(addSuffixAfterName)
					message += onlinePlayer.getDisplayName() + (addSuffix?handler.getSuffix(onlinePlayer):"") + "&f";
				else
					message += (addSuffix?handler.getSuffix(onlinePlayer):"") + onlinePlayer.getDisplayName() + "&f";
			}
		}
		return message.substring(2);
	}

	private static Random random = new Random();

	/**
	 * Gets a String that represents the current time in the specified world.
	 * @param world the world to get the time from
	 * @return a time string that represents the time in the specified world.
	 */
	public static String getWorldTimeString(World world)
	{
	    int hours = (int)((world.getTime()/1000+8)%24);
	    int minutes = (int)(60*(world.getTime()%1000)/1000);
	    long days = (long)((world.getFullTime()/1000+8)/24);
	    long months = days/30;
	    long years = months/12;
	    return String.format("%02d-%02d-%04d CE %d:%02d %s",
	            (months % 12) + 1, (days % 30) + 1, years + 2010, (hours % 12) == 0 ? 12 : hours % 12, minutes,
	            hours < 12 ? "AM" : "PM");
	}

}
