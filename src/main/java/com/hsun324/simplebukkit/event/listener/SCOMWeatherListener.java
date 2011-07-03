package com.hsun324.simplebukkit.event.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.weather.*;

import com.hsun324.simplebukkit.bindings.EventBindings;

public class SCOMWeatherListener extends WeatherListener
{
	private static final Map<Priority, SCOMWeatherListener> instanceMap = new HashMap<Priority, SCOMWeatherListener>();
	public static SCOMWeatherListener getInstance(Priority priority)
	{
		if(instanceMap.containsKey(priority))
			return instanceMap.get(priority);
		instanceMap.put(priority, new SCOMWeatherListener(priority));
		return instanceMap.get(priority);
	}
	
	private final Priority priority;
	private SCOMWeatherListener(Priority priority)
	{
		this.priority = priority;
	}
	
	@Override
	public void onWeatherChange(WeatherChangeEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onThunderChange(ThunderChangeEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onLightningStrike(LightningStrikeEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
}
