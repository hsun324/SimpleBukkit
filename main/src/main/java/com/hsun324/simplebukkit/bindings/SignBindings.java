package com.hsun324.simplebukkit.bindings;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.hsun324.simplebukkit.SimpleBukkitPlugin;
import com.hsun324.simplebukkit.permissions.PermissionsHandler;
import com.hsun324.simplebukkit.player.ColoredMessageSender;

/**
 * A old class that handles signs.
 * @author hsun324
 * @deprecated
 */
public class SignBindings
{
	private static final SignBindings instance = new SignBindings();
	public static SignBindings getInstance()
	{
		return instance;
	}

	private CommandList signs = new CommandList();
	
	public void addSignClass(Class<?> commandClass)
	{
		for(Method function : commandClass.getMethods())
			signs.addSign(function);
	}
	public void removeSignClass(Class<?> commandClass)
	{
		for(Method function : commandClass.getMethods())
			signs.removeSign(function);
	}
	public boolean executeSign(Player player, Sign sign)
	{
		return signs.executeSign(player, sign);
	}
	public void clear()
	{
		signs.clear();
	}
	@Retention(RetentionPolicy.RUNTIME)
	public @interface SignDeclaration
	{
		String[] aliases();
		String[] usage();
		String description();
		int color() default 0;
	}

	private class CommandList
	{
		private HashMap<SignDeclaration, Method> signs = new HashMap<SignDeclaration, Method>();
		protected boolean addSign(Method function)
		{
			if(function.isAnnotationPresent(SignDeclaration.class))
			{
				SignDeclaration declaration = function.getAnnotation(SignDeclaration.class);
				if(declaration.aliases().length > 0)
				{
					if(!signs.containsKey(declaration))
					{
						Class<?>[] params = function.getParameterTypes();
						if(params.length == 4 && Player.class.isAssignableFrom(params[0]) && 
								                 String.class.isAssignableFrom(params[1]) && 
								                 (new String[0]).getClass().isAssignableFrom(params[2]) && 
								                 ErrorType.class.isAssignableFrom(function.getReturnType()))
							signs.put(declaration, function);
						else
							SimpleBukkitPlugin.getInstance().getLogger().info("[NAG] Author of sign " + declaration.aliases()[0] + " should update their command.");
					}
				}
			}
			return false;
		}
		protected void removeSign(Method function)
		{
			if(function.isAnnotationPresent(SignDeclaration.class))
				signs.remove(function.getAnnotation(SignDeclaration.class));
		}
	
		private Entry<SignDeclaration, Method> getSign(String name)
		{
			for(Entry<SignDeclaration, Method> entry : signs.entrySet())
			{
				SignDeclaration declaration = entry.getKey();
				for(String alias : declaration.aliases())
					if(alias.equalsIgnoreCase(name))
						return entry;
			}
			return null;
		}
		
		protected boolean executeSign(Player player, Sign sign)
		{
			String[] lines = sign.getLines();
			if(!lines[0].matches("^(\u00A7[a-fA-F0-9])?\\[[A-Z][a-z]*\\]$"))
				return false;
			lines[0].replaceAll("\u00A7[a-fA-F0-9]|\\[|\\]", "");
			Entry<SignDeclaration, Method> entry = null;
			if((entry = getSign(lines[0])) != null)
			{
				SignDeclaration declaration = entry.getKey();
				if(!PermissionsHandler.getInstance().hasPermission(player, "signs." + declaration.aliases()[0].toLowerCase() + ".execute"))
				{
					ColoredMessageSender.sendErrorMessage(player, "You do not have permission to execute that.");
					return true;
				}
				try
				{
					Object result = entry.getValue().invoke(null, player, lines[0], new String[]{lines[1], lines[2], lines[3]});
					if(result instanceof ErrorType)
					{
						ErrorType commandResult = (ErrorType)result;
						if(commandResult.isSyntaxError())
						{
							ColoredMessageSender.sendErrorMessage(player, commandResult.getMessage());
							outputUsage(player, declaration);
						}
						else if(!commandResult.isSuccessful())
						{
							ColoredMessageSender.sendErrorMessage(player, commandResult.getMessage());
						}
					}
				}
				catch (IllegalArgumentException e)
				{
					SimpleBukkitPlugin.getInstance().getLogger().warning("Sign " + lines[0] + " has a function with an invalid signature.");
					SimpleBukkitPlugin.getInstance().getLogger().info("[NAG] Author of sign " + lines[0] + " should update their command.");
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					SimpleBukkitPlugin.getInstance().getLogger().warning("Internal Server Error");
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					e.getCause().printStackTrace();
				}
				return true;
			}
			return false;
		}
	
		private void outputUsage(Player player, SignDeclaration declaration)
		{
			if(declaration.description().trim().length() > 0)
				ColoredMessageSender.sendErrorMessage(player, declaration.description());
			ColoredMessageSender.sendErrorMessage(player, declaration.aliases()[0]);
			int i = 1;
			for(String usage : declaration.usage())
				if(i++ < 4 && !usage.trim().isEmpty())
					ColoredMessageSender.sendErrorMessage(player, "Line " + i + ": " + usage.trim());
		}
	
		public void clear()
		{
			signs.clear();
		}
	}
}
