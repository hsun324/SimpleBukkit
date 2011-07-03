package com.hsun324.simplebukkit.api;


import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

import com.hsun324.simplebukkit.api.enums.MobType;
import com.hsun324.simplebukkit.api.enums.WeatherType;

public class SBWorld
{
	private World world;
	public SBWorld(World world)
	{
		this.world = world;
	}
	
	public void setWeather(WeatherType weather)
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
	public WeatherType getWeather()
	{
		if(!world.hasStorm())
			return WeatherType.CLEAR;
		if(world.isThundering())
			return WeatherType.THUNDERING;
		return WeatherType.RAINY;
	}
	public LivingEntity spawnMob(MobType type, Location location)
	{
		return type.spawn(world, location);
	}
	
}
