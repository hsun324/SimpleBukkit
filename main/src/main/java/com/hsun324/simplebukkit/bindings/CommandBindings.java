package com.hsun324.simplebukkit.bindings;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
	public boolean executeCommand(CommandSender sender, Command command, String title, String[] arguments)
	{
		if(sender instanceof Player)
			return commands.executeCommand((Player) sender, command.getLabel(), arguments);
		return false;
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
		String[] flags();
		int minArgs() default 0;
		int maxArgs() default 0;
		String basePermission() default "";
		String usage() default "";
		boolean enableConsole() default false;
	}

	public class FlagList
	{
		protected HashMap<String, List<String>> flagMap = new HashMap<String, List<String>>();
		protected List<String> last = null;
		protected void add(String flag, List<String> args)
		{
			flagMap.put(flag, args);
			last = args;
		}
		protected boolean addToLast(String arg)
		{
			if(last != null)
			{
				last.add(arg);
				return true;
			}
			return false;
		}
		public boolean has(String flag)
		{
			return flagMap.containsKey(flag);
		}
		public List<String> get(String flag)
		{
			if(flagMap.containsKey(flag))
				return flagMap.get(flag);
			return null;
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
		
		protected String[] encapsulatorTypes = new String[]{"\"", "'"};
		protected boolean[] allowEscapes = new boolean[]{true, false};
		
		private boolean endsQuote(String arg, String encapsulatorType, boolean allowEscape)
		{
			return (!arg.endsWith("\\" + encapsulatorType) || !allowEscape) && arg.endsWith(encapsulatorType);
		}
		
		protected boolean executeCommand(Player player, String command, String[] arguments)
		{
			Entry<CommandDeclaration, Method> entry = null;
			if((entry = getCommand(command)) != null)
			{
				FlagList flags = new FlagList();
				List<String> argslist = new ArrayList<String>();
				for(int i = 0; i < arguments.length; i++)
				{
					String argument = arguments[i];
					for(int j = 0; j < encapsulatorTypes.length; j++)
					{
						String encapsulatorType = encapsulatorTypes[j];
						boolean allowEscape = allowEscapes[j];
						if(argument.startsWith(encapsulatorType))
						{
							while(i + 1 < arguments.length && !endsQuote(arguments[i], encapsulatorType, allowEscape))
								argument += " " + arguments[++i];
							if(!endsQuote(argument, encapsulatorType, allowEscape))
							{
								ColoredMessageSender.sendErrorMessage(player, "Invalid Syntax: Unclosed Encapsulator");
								return true;
							}
							argument = argument.substring(1, argument.length() - 1);
							if(allowEscape)
								argument = argument.replaceAll("\\\\" + encapsulatorType, encapsulatorType);
							break;
						}
					}
					
					if(argument.startsWith("-") && argument.length() > 1)
					{
						String flag = argument.substring(1);
						if(contains(entry.getKey().flags(), flag))
							flags.add(flag, new ArrayList<String>());
						else
						{
							ColoredMessageSender.sendErrorMessage(player, "Invalid Syntax: No Such Flag \"" + flag + "\"");
							return true;
						}
					}
					else
						if(!flags.addToLast(argument))
							argslist.add(argument);
				}
				String[] args = argslist.toArray(new String[0]);
				
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
			ColoredMessageSender.sendErrorMessage(player, getUsage(declaration));
		}

		private boolean contains(Object[] array, Object obj)
		{
			for(Object aobj : array)
				if(aobj.equals(obj))
					return true;
			return false;
		}
		
		private String getUsage(CommandDeclaration declaration)
		{
			String message = "/" + declaration.aliases()[0];
			if(declaration.usage().trim().length() > 0)
				message += " " + declaration.usage();
			return message;
		}
	
		public void clear()
		{
			commands.clear();
		}
	}
}
