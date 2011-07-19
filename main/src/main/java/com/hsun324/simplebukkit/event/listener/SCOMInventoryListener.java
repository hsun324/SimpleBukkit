package com.hsun324.simplebukkit.event.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.inventory.*;

import com.hsun324.simplebukkit.bindings.EventBindings;

public final class SCOMInventoryListener extends InventoryListener
{
	private static final Map<Priority, SCOMInventoryListener> instanceMap = new HashMap<Priority, SCOMInventoryListener>();
	public static SCOMInventoryListener getInstance(Priority priority)
	{
		if(instanceMap.containsKey(priority))
			return instanceMap.get(priority);
		instanceMap.put(priority, new SCOMInventoryListener(priority));
		return instanceMap.get(priority);
	}
	
	private final Priority priority;
	private SCOMInventoryListener(Priority priority)
	{
		this.priority = priority;
	}
	
	@Override
	public void onFurnaceBurn(FurnaceBurnEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onFurnaceSmelt(FurnaceSmeltEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	/*
	@Override
	public void onInventoryChange(InventoryChangeEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onInventoryClick(InventoryClickEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onInventoryClose(InventoryCloseEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onInventoryOpen(InventoryOpenEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	*/
}
