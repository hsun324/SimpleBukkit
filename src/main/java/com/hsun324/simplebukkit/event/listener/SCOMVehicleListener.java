package com.hsun324.simplebukkit.event.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.vehicle.*;

import com.hsun324.simplebukkit.bindings.EventBindings;

public class SCOMVehicleListener extends VehicleListener
{
	private static final Map<Priority, SCOMVehicleListener> instanceMap = new HashMap<Priority, SCOMVehicleListener>();
	public static SCOMVehicleListener getInstance(Priority priority)
	{
		if(instanceMap.containsKey(priority))
			return instanceMap.get(priority);
		instanceMap.put(priority, new SCOMVehicleListener(priority));
		return instanceMap.get(priority);
	}
	
	private final Priority priority;
	private SCOMVehicleListener(Priority priority)
	{
		this.priority = priority;
	}
	
	@Override
	public void onVehicleCreate(VehicleCreateEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onVehicleDamage(VehicleDamageEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onVehicleBlockCollision(VehicleBlockCollisionEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onVehicleEntityCollision(VehicleEntityCollisionEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onVehicleEnter(VehicleEnterEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onVehicleExit(VehicleExitEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onVehicleMove(VehicleMoveEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onVehicleDestroy(VehicleDestroyEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onVehicleUpdate(VehicleUpdateEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
}
