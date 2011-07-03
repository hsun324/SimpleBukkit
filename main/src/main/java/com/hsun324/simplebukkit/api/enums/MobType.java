package com.hsun324.simplebukkit.api.enums;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.LivingEntity;

public enum MobType
{
	CHICKEN(Faction.FRIENDLY, "Chicken", CreatureType.CHICKEN),
	COW(Faction.FRIENDLY, "Cow", CreatureType.COW),
	PIG(Faction.FRIENDLY, "Pig", CreatureType.PIG),
	SHEEP(Faction.FRIENDLY, "Sheep", CreatureType.SHEEP),
	SQUID(Faction.FRIENDLY, "Squid", CreatureType.SQUID),
	TAMEDWOLF(Faction.ALLIED, "TamedWolf", CreatureType.WOLF),
	PIG_ZOMBIE(Faction.NEUTRAL, "ZombiePig", CreatureType.PIG_ZOMBIE),
	WOLF(Faction.NEUTRAL, "Wolf", CreatureType.WOLF),
	CREEPER(Faction.HOSTILE, "Creeper", CreatureType.CREEPER),
	GHAST(Faction.HOSTILE, "Ghast", CreatureType.GHAST),
	SKELETON(Faction.HOSTILE, "Skeleton", CreatureType.SKELETON),
	SLIME(Faction.HOSTILE, "Slime", CreatureType.SLIME),
	SPIDER(Faction.HOSTILE, "Spider", CreatureType.SPIDER),
	ZOMBIE(Faction.HOSTILE, "Zombie", CreatureType.ZOMBIE),
	MONSTER(Faction.HOSTILE, "Monster", CreatureType.MONSTER);
	
	private static Map<String, MobType> stringIndex = new HashMap<String, MobType>();
	private static Map<CreatureType, MobType> bukkitTypeIndex = new HashMap<CreatureType, MobType>();
	
	private Faction faction;
	private String name;
	private CreatureType type;
	private MobType(Faction faction, String name, CreatureType type)
	{
		this.faction = faction;
		this.name = name;
		this.type = type;
	}
	
	public Faction getFaction()
	{
		return faction;
	}
	
	public String getName()
	{
		return name;
	}
	
	public LivingEntity spawn(World world, Location location)
	{
		return world.spawnCreature(location, type);
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
	public static MobType getMob(String name)
	{
		return stringIndex.get(name);
	}
	
	public static MobType getMob(CreatureType type)
	{
		return bukkitTypeIndex.get(type);
	}
	
	static
	{
		for(MobType type : values())
		{
			stringIndex.put(type.name, type);
			bukkitTypeIndex.put(type.type, type);
		}
	}
}
