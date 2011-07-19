package com.hsun324.simplebukkit.event.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.block.*;

import com.hsun324.simplebukkit.bindings.EventBindings;

public final class SCOMBlockListener extends BlockListener
{
	private static final Map<Priority, SCOMBlockListener> instanceMap = new HashMap<Priority, SCOMBlockListener>();
	public static SCOMBlockListener getInstance(Priority priority)
	{
		if(instanceMap.containsKey(priority))
			return instanceMap.get(priority);
		instanceMap.put(priority, new SCOMBlockListener(priority));
		return instanceMap.get(priority);
	}
	
	private final Priority priority;
	private SCOMBlockListener(Priority priority)
	{
		this.priority = priority;
	}
	
	@Override
	public void onBlockBreak(BlockBreakEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onBlockBurn(BlockBurnEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onBlockCanBuild(BlockCanBuildEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onBlockDamage(BlockDamageEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onBlockDispense(BlockDispenseEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onBlockFromTo(BlockFromToEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onBlockPhysics(BlockPhysicsEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onBlockPlace(BlockPlaceEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onBlockRedstoneChange(BlockRedstoneEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onLeavesDecay(LeavesDecayEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onSnowForm(SnowFormEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onSignChange(SignChangeEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onBlockFade(BlockFadeEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onBlockForm(BlockFormEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
	
	@Override
	public void onBlockSpread(BlockSpreadEvent event)
	{
		EventBindings.getInstance().callEvent(event.getType(), event, priority);
	}
}
