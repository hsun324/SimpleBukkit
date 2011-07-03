package com.hsun324.simplebukkit.event.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.world.*;

import com.hsun324.simplebukkit.bindings.EventBindings;

public class SCOMWorldListener extends WorldListener
{
	private static final Map<Priority, SCOMWorldListener> instanceMap = new HashMap<Priority, SCOMWorldListener>();
	public static SCOMWorldListener getInstance(Priority priority)
	{
		if(instanceMap.containsKey(priority))
			return instanceMap.get(priority);
		instanceMap.put(priority, new SCOMWorldListener(priority));
		return instanceMap.get(priority);
	}
	
	private final Priority priority;
	private SCOMWorldListener(Priority priority)
	{
		this.priority = priority;
	}
	@Override
	public void onChunkLoad(ChunkLoadEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onChunkPopulate(ChunkPopulateEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onChunkUnload(ChunkUnloadEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPortalCreate(PortalCreateEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onSpawnChange(SpawnChangeEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onWorldSave(WorldSaveEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onWorldInit(WorldInitEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onWorldLoad(WorldLoadEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onWorldUnload(WorldUnloadEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
}
