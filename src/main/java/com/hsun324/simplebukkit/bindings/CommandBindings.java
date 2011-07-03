package com.hsun324.simplebukkit.bindings;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

import com.hsun324.simplebukkit.SimpleBukkitPlugin;
import com.hsun324.simplebukkit.permissions.PermissionsHandler;
import com.hsun324.simplebukkit.player.ColoredMessageSender;

public class CommandBindings
{
	private static final CommandBindings instance = new CommandBindings();
	public static CommandBindings getInstance()
	{
		return instance;
	}

	private CommandList commands = new CommandList();
	
	public void addCommandClass(Class<?> commandClass)
	{
		for(Method function : commandClass.getMethods())
			commands.addCommand(function);
	}
	public void removeCommandClass(Class<?> commandClass)
	{
		for(Method function : commandClass.getMethods())
			commands.removeCommand(function);
	}
	public boolean executeCommand(Player player, String command, String[] arguments)
	{
		return commands.executeCommand(player, command, arguments);
	}
	public void clear()
	{
		commands.clear();
	}
	@Retention(RetentionPolicy.RUNTIME)
	public @interface CommandDeclaration
	{
		String[] aliases();
		String description() default "";
		char[] flags();
		int minArgs() default 0;
		int maxArgs() default 0;
		String basePermission() default "";
		String usage() default "";
	}

	public class FlagList
	{
		protected HashMap<Character, Boolean> FlagMap = new HashMap<Character, Boolean>();
		public void add(char flag, boolean set)
		{
			FlagMap.put(Character.valueOf(flag), Boolean.valueOf(set));
		}
		public boolean get(char flag)
		{
			if(FlagMap.containsKey(flag))
				return FlagMap.get(flag).booleanValue();
			return false;
		}
	}

	private class CommandList
	{
		private HashMap<CommandDeclaration, Method> commands = new HashMap<CommandDeclaration, Method>();
		protected boolean addCommand(Method function)
		{
			if(function.isAnnotationPresent(CommandDeclaration.class))
			{
				CommandDeclaration declaration = function.getAnnotation(CommandDeclaration.class);
				if(declaration.aliases().length > 0)
				{
					if(!commands.containsKey(declaration))
					{
						Class<?>[] params = function.getParameterTypes();
						if(params.length == 4 && Player.class.isAssignableFrom(params[0]) && String.class.isAssignableFrom(params[1]) && (new String[0]).getClass().isAssignableFrom(params[2]) && FlagList.class.isAssignableFrom(params[3]) && ErrorType.class.isAssignableFrom(function.getReturnType()))
							commands.put(declaration, function);
						else
							SimpleBukkitPlugin.getInstance().getLogger().info("[NAG] Author of command " + declaration.aliases()[0] + " should update their command.");
					}
				}
				else
					SimpleBukkitPlugin.getInstance().getLogger().info("[NAG] Author of command " + declaration.aliases()[0] + " should update their command.");
			}
			return false;
		}
		protected void removeCommand(Method function)
		{
			if(function.isAnnotationPresent(CommandDeclaration.class))
				commands.remove(function.getAnnotation(CommandDeclaration.class));
		}
	
		private Entry<CommandDeclaration, Method> getCommand(String command)
		{
			for(Entry<CommandDeclaration, Method> entry : commands.entrySet())
			{
				CommandDeclaration declaration = entry.getKey();
				for(String alias : declaration.aliases())
					if(alias.equalsIgnoreCase(command))
						return entry;
			}
			return null;
		}
		
		protected boolean executeCommand(Player player, String command, String[] arguments)
		{
			FlagList flags = new FlagList();
			List<String> argslist = new ArrayList<String>();
			for(int i = 0; i < arguments.length; i++)
			{
				if(arguments[i].startsWith("-") && arguments[i].length() == 2)
					flags.add(arguments[i].toCharArray()[1], true);
				else
					argslist.add(arguments[i]);
			}
			String[] args = argslist.toArray(new String[0]);
			
			Entry<CommandDeclaration, Method> entry = null;
			if((entry = getCommand(command)) != null)
			{
				CommandDeclaration declaration = entry.getKey();
				if(declaration.maxArgs() < args.length)
				{
					ColoredMessageSender.sendErrorMessage(player, "Too many arguments!");
					outputUsage(player, declaration); 
					return true;
				}
				if(declaration.minArgs() > args.length)
				{
					ColoredMessageSender.sendErrorMessage(player, "Not enough arguments!");
					outputUsage(player, declaration); 
					return true;
				}
				if(!declaration.basePermission().trim().isEmpty() && !PermissionsHandler.getInstance().hasPermission(player, declaration.basePermission()))
				{
					ColoredMessageSender.sendErrorMessage(player, "You do not have permission to use this command.");
					return true;
				}
				try
				{
					Object result = entry.getValue().invoke(null, player, command, args, flags);
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
					SimpleBukkitPlugin.getInstance().getLogger().warning("Command " + command + " has a function with an invalid signature.");
					SimpleBukkitPlugin.getInstance().getLogger().info("[NAG] Author of command " + command + " should update their command.");
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
	
		private void outputUsage(Player player, CommandDeclaration declaration)
		{
			if(declaration.description().trim().length() > 0)
				ColoredMessageSender.sendErrorMessage(player, declaration.description());
			String message = "/" + declaration.aliases()[0] + " ";
			if(declaration.flags().length > 0)
			{
				for(char flag : declaration.flags ())
					message += "-" + flag;
				message += " ";
			}
			if(declaration.usage().trim().length() > 0)
				message += declaration.usage();
			ColoredMessageSender.sendErrorMessage(player, message);
		}
	
		public void clear()
		{
			commands.clear();
		}
	}
}
