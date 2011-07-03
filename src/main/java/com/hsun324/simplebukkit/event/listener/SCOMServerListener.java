package com.hsun324.simplebukkit.event.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.server.*;

import com.hsun324.simplebukkit.bindings.EventBindings;

public class SCOMServerListener extends ServerListener
{
	private static final Map<Priority, SCOMServerListener> instanceMap = new HashMap<Priority, SCOMServerListener>();
	public static SCOMServerListener getInstance(Priority priority)
	{
		if(instanceMap.containsKey(priority))
			return instanceMap.get(priority);
		instanceMap.put(priority, new SCOMServerListener(priority));
		return instanceMap.get(priority);
	}
	
	private final Priority priority;
	private SCOMServerListener(Priority priority)
	{
		this.priority = priority;
	}
	
	@Override
	public void onPluginEnable(PluginEnableEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPluginDisable(PluginDisableEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onServerCommand(ServerCommandEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
}
