package com.hsun324.simplebukkit.event.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.player.*;

import com.hsun324.simplebukkit.bindings.EventBindings;

public class SCOMPlayerListener extends PlayerListener
{
	private static final Map<Priority, SCOMPlayerListener> instanceMap = new HashMap<Priority, SCOMPlayerListener>();
	public static SCOMPlayerListener getInstance(Priority priority)
	{
		if(instanceMap.containsKey(priority))
			return instanceMap.get(priority);
		instanceMap.put(priority, new SCOMPlayerListener(priority));
		return instanceMap.get(priority);
	}
	
	private final Priority priority;
	private SCOMPlayerListener(Priority priority)
	{
		this.priority = priority;
	}
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerKick(PlayerKickEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerChat(PlayerChatEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerMove(PlayerMoveEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerPreLogin(PlayerPreLoginEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerEggThrow(PlayerEggThrowEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerAnimation(PlayerAnimationEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onInventoryOpen(PlayerInventoryEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onItemHeldChange(PlayerItemHeldEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerDropItem(PlayerDropItemEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerPickupItem(PlayerPickupItemEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerBucketFill(PlayerBucketFillEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerBedEnter(PlayerBedEnterEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerBedLeave(PlayerBedLeaveEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
}
