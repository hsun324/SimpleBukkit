package com.hsun324.simplebukkit.bindings.event;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Type;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.hsun324.simplebukkit.bindings.CommandBindings;
import com.hsun324.simplebukkit.bindings.EventBindings.EventDeclaration;

public class CommandCaptureEvent
{
	@EventDeclaration(Type.PLAYER_COMMAND_PREPROCESS)
	public static void chatCommand(PlayerCommandPreprocessEvent event)
	{
		if(event.isCancelled() || !event.getMessage().startsWith("/"))
			return;
		String message = event.getMessage();
		String[] parts = message.split(" ");
		String[] args = new String[0];
		if(parts.length > 1)
		{
			args = new String[parts.length - 1];
			for(int i = 1; i < parts.length; i++)
				args[i-1] = parts[i];
		}
		List<String> processedArgs = new ArrayList<String>();
		for(String arg : args)
			if(!arg.trim().isEmpty())
				processedArgs.add(arg);
		if(CommandBindings.getInstance().executeCommand((Player)event.getPlayer(), parts[0].substring(1), processedArgs.toArray(new String[0])))
			event.setCancelled(true);
	}
}

