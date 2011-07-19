package com.hsun324.simplebukkit.bindings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.*;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.hsun324.simplebukkit.SimpleBukkitPlugin;
import com.hsun324.simplebukkit.event.listener.SCOMBlockListener;
import com.hsun324.simplebukkit.event.listener.SCOMEntityListener;
import com.hsun324.simplebukkit.event.listener.SCOMInventoryListener;
import com.hsun324.simplebukkit.event.listener.SCOMPlayerListener;
import com.hsun324.simplebukkit.event.listener.SCOMServerListener;
import com.hsun324.simplebukkit.event.listener.SCOMVehicleListener;
import com.hsun324.simplebukkit.event.listener.SCOMWeatherListener;
import com.hsun324.simplebukkit.event.listener.SCOMWorldListener;

public class EventBindings
{

	private static final EventBindings instance = new EventBindings();
	public static EventBindings getInstance()
	{
		return instance;
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface EventDeclaration
	{
		Event.Type value();
		Event.Priority priority() default Event.Priority.Normal;
	}
	
	public static class EventDescriptor
	{
		private final Type type;
		private final Priority priority;
		
		public EventDescriptor(Type type, Priority priority)
		{
			this.type = type;
			this.priority = priority;
		}
		public EventDescriptor(EventDeclaration annot)
		{
			this.type = annot.value();
			this.priority = annot.priority();
		}
		
		public Type getType()
		{
			return type;
		}
		public Priority getPriority()
		{
			return priority;
		}
		
		@Override
		public String toString()
		{
			return this.getClass().getName() + "[type:" + type.toString() + ",priority" + priority.toString() + "]";
		}
		@Override
		public boolean equals(Object obj)
		{
			return obj instanceof EventDescriptor && equals((EventDescriptor) obj);
		}
		private boolean equals(EventDescriptor desc)
		{
			return desc.priority == priority && desc.type == type;
		}
		@Override
		public int hashCode()
		{
			return (priority.hashCode() << 16) ^ type.hashCode();
		}
	}
	
	private final EventBindingList bindingList = new EventBindingList();
	
	public void addEventClass(Class<?> eventClass)
	{
		for(Method function : eventClass.getMethods())
			bindingList.addEvent(function);
	}
	
	public void removeEventClass(Class<?> eventClass)
	{
		for(Method function : eventClass.getMethods())
			bindingList.removeEvent(function);
	}
	
	public void callEvent(Type type, Event event, Priority priority)
	{
		bindingList.callEvent(type, event, priority);
	}
	protected class EventBindingList
	{
		public class Entry<Key, Value>
		{
			private final Key key;
			private final Value value;
			public Entry(Key key, Value value)
			{
				this.key = key;
				this.value = value;
			}
			public Key getKey()
			{
				return key;
			}
			public Value getValue()
			{
				return value;
			}
			
			@Override
			public String toString()
			{
				return "Entry<" + key.getClass().getName() + ", " + value.getClass().getName() + ">[key:" + key.toString() + ",value:" + value.toString() + "]";
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public boolean equals(Object obj)
			{
				return obj instanceof Entry && ((Entry) obj).key.equals(key) && ((Entry) obj).value.equals(value);
			}
			@Override
			public int hashCode()
			{
				return (key.hashCode() << 16) ^ value.hashCode();
			}
		}
		private List<Entry<EventDescriptor, Method>> eventList = new ArrayList<Entry<EventDescriptor, Method>>();
		public void addEvent(Method function)
		{
			if(function.isAnnotationPresent(EventDeclaration.class))
				eventList.add(new Entry<EventDescriptor, Method>(new EventDescriptor(function.getAnnotation(EventDeclaration.class)), function));
		}
		public void removeEvent(Method function)
		{
			if(function.isAnnotationPresent(EventDeclaration.class))
				eventList.remove(new Entry<EventDescriptor, Method>(new EventDescriptor(function.getAnnotation(EventDeclaration.class)), function));
		}
		public void callEvent(Type type, Event event, Priority priority)
		{
			for(Entry<EventDescriptor, Method> entry : eventList)
			{
				if(entry.getKey().type == type && entry.getKey().getPriority() == priority)
				{
					try
					{
						entry.getValue().invoke(null, event);
					}
					catch (IllegalArgumentException e)
					{
						SimpleBukkitPlugin.getInstance().getLogger().info("[NAG] Author of event " + entry.getValue() + " should update their event.");
						SimpleBukkitPlugin.getInstance().getLogger().warning("Event " + entry.getValue() + " is not in the correct format.");
						e.printStackTrace();
					}
					catch (IllegalAccessException e)
					{
						SimpleBukkitPlugin.getInstance().getLogger().info("[NAG] Author of event " + entry.getValue() + " should update their event.");
						SimpleBukkitPlugin.getInstance().getLogger().warning("Event " + entry.getValue() + " is not public.");
					}
					catch (InvocationTargetException e)
					{
						SimpleBukkitPlugin.getInstance().getLogger().info("[NAG] Author of event " + entry.getValue() + " should update their event.");
						e.getCause().printStackTrace();
					}
					catch (NullPointerException e)
					{
						SimpleBukkitPlugin.getInstance().getLogger().info("[NAG] Author of event " + entry.getValue() + " should update their event.");
						SimpleBukkitPlugin.getInstance().getLogger().warning("Event " + entry.getValue() + " is not static.");
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
	public void createListeners(Plugin plugin, PluginManager pluginManager, Priority priority)
	{
		pluginManager.registerEvent(Type.BLOCK_BREAK, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.BLOCK_BURN, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.BLOCK_CANBUILD, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.BLOCK_DAMAGE, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.BLOCK_DISPENSE, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.BLOCK_FADE, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.BLOCK_FORM, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.BLOCK_FROMTO, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.BLOCK_IGNITE, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.BLOCK_PHYSICS, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.BLOCK_PLACE, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.BLOCK_SPREAD, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.LEAVES_DECAY, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.REDSTONE_CHANGE, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.SNOW_FORM, SCOMBlockListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.SIGN_CHANGE, SCOMBlockListener.getInstance(priority), priority, plugin);
		
		pluginManager.registerEvent(Type.CHUNK_LOAD, SCOMWorldListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.CHUNK_POPULATED, SCOMWorldListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.CHUNK_UNLOAD, SCOMWorldListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PORTAL_CREATE, SCOMWorldListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.SPAWN_CHANGE, SCOMWorldListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.WORLD_INIT, SCOMWorldListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.WORLD_LOAD, SCOMWorldListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.WORLD_SAVE, SCOMWorldListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.WORLD_UNLOAD, SCOMWorldListener.getInstance(priority), priority, plugin);
		
		pluginManager.registerEvent(Type.CREATURE_SPAWN, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.CREEPER_POWER, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.ENTITY_COMBUST, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.ENTITY_DAMAGE, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.ENTITY_DEATH, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.ENTITY_EXPLODE, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.ENTITY_INTERACT, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.ENTITY_PORTAL_ENTER, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.ENTITY_REGAIN_HEALTH, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.ENTITY_TAME, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.ENTITY_TARGET, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.EXPLOSION_PRIME, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PAINTING_BREAK, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PAINTING_PLACE, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PIG_ZAP, SCOMEntityListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PROJECTILE_HIT, SCOMEntityListener.getInstance(priority), priority, plugin);
		
		pluginManager.registerEvent(Type.FURNACE_BURN, SCOMInventoryListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.FURNACE_SMELT, SCOMInventoryListener.getInstance(priority), priority, plugin);
		//pluginManager.registerEvent(Type.INVENTORY_CHANGE, SCOMInventoryListener.getInstance(priority), priority, plugin);
		//pluginManager.registerEvent(Type.INVENTORY_CLICK, SCOMInventoryListener.getInstance(priority), priority, plugin);
		//pluginManager.registerEvent(Type.INVENTORY_CLOSE, SCOMInventoryListener.getInstance(priority), priority, plugin);
		//pluginManager.registerEvent(Type.INVENTORY_OPEN, SCOMInventoryListener.getInstance(priority), priority, plugin);
		
		pluginManager.registerEvent(Type.LIGHTNING_STRIKE, SCOMWeatherListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.THUNDER_CHANGE, SCOMWeatherListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.WEATHER_CHANGE, SCOMWeatherListener.getInstance(priority), priority, plugin);
		
		pluginManager.registerEvent(Type.PLAYER_ANIMATION, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_BED_ENTER, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_BED_LEAVE, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_BUCKET_EMPTY, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_BUCKET_FILL, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_CHAT, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_COMMAND_PREPROCESS, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_DROP_ITEM, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_EGG_THROW, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_INTERACT, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_INTERACT_ENTITY, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_ITEM_HELD, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_JOIN, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_KICK, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_LOGIN, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_MOVE, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_PICKUP_ITEM, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_PORTAL, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_PRELOGIN, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_QUIT, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_RESPAWN, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_TELEPORT, SCOMPlayerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLAYER_TOGGLE_SNEAK, SCOMPlayerListener.getInstance(priority), priority, plugin);
		
		pluginManager.registerEvent(Type.PLUGIN_DISABLE, SCOMServerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.PLUGIN_ENABLE, SCOMServerListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.SERVER_COMMAND, SCOMServerListener.getInstance(priority), priority, plugin);
		
		pluginManager.registerEvent(Type.VEHICLE_COLLISION_BLOCK, SCOMVehicleListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.VEHICLE_COLLISION_ENTITY, SCOMVehicleListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.VEHICLE_CREATE, SCOMVehicleListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.VEHICLE_DAMAGE, SCOMVehicleListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.VEHICLE_DESTROY, SCOMVehicleListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.VEHICLE_ENTER, SCOMVehicleListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.VEHICLE_EXIT, SCOMVehicleListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.VEHICLE_MOVE, SCOMVehicleListener.getInstance(priority), priority, plugin);
		pluginManager.registerEvent(Type.VEHICLE_UPDATE, SCOMVehicleListener.getInstance(priority), priority, plugin);
	}
}
