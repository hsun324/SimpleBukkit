package com.hsun324.simplebukkit.event.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.entity.*;
import org.bukkit.event.painting.*;

import com.hsun324.simplebukkit.bindings.EventBindings;

public final class SCOMEntityListener extends EntityListener
{
	private static final Map<Priority, SCOMEntityListener> instanceMap = new HashMap<Priority, SCOMEntityListener>();
	public static SCOMEntityListener getInstance(Priority priority)
	{
		if(instanceMap.containsKey(priority))
			return instanceMap.get(priority);
		instanceMap.put(priority, new SCOMEntityListener(priority));
		return instanceMap.get(priority);
	}
	
	private final Priority priority;
	private SCOMEntityListener(Priority priority)
	{
		this.priority = priority;
	}
	
	@Override
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onEntityCombust(EntityCombustEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onEntityDamage(EntityDamageEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onEntityExplode(EntityExplodeEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onExplosionPrime(ExplosionPrimeEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onEntityDeath(EntityDeathEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onEntityPortalEnter(EntityPortalEnterEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onEntityRegainHealth(EntityRegainHealthEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onEntityTame(EntityTameEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onEntityTarget(EntityTargetEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onEntityInteract(EntityInteractEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPaintingPlace(PaintingPlaceEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPaintingBreak(PaintingBreakEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPigZap(PigZapEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onCreeperPower(CreeperPowerEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onProjectileHit(ProjectileHitEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
}
