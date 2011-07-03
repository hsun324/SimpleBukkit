package com.hsun324.simplebukkit.util;


import java.util.ArrayList;
import java.util.List;



/**
 * A common utilities class for MineCraft server plugins.
 * @author hsun324
 * @version 2.4b
 */
public final class Util
{
	/**
	 * Formats the given string with MineCraft compatible colors.
	 * 
	 * 
	 * @param string
	 * @return
	 */
	public static String formatWithColors(String string)
	{
		new Object().equals("");
		return string.replaceAll("&([0-9A-Fa-f])", "\u00A7$1");
	}
	
	public static String formatEnumName(Enum<?> theEnum)
	{
		return formatEnumName(theEnum.name());
	}
	
	public static String formatEnumName(String Name)
	{
		String Out = "";
		String[] Split = Name.toLowerCase().replace('_', ' ').split(" ");
		Name = "";
		for(String S : Split)
			if(S.length() > 1)
				Out += " " + S.substring(0, 1).toUpperCase() + S.substring(1);
			else
				Out += " " + S;
		return Out.substring(1);
	}
	public static String getSimpleEnumName(String name)
	{
		return name.replace("_", "").toLowerCase();
	}
	
	public static <T> List<T> createListFromArray(T[] array)
	{
		List<T> list = new ArrayList<T>();
		for(T item : array)
			list.add(item);
		return list;
	}
}
