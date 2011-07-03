package com.hsun324.simplebukkit.util;

import java.util.ArrayList;
import java.util.List;

public final class IntegerUtils
{
	/**
	 * Parses the given String into a integer.
	 * @param stringNumber the number to parse
	 * @return the parsed string if successful. {@link Integer#MIN_VALUE} if not.
	 */
	public static int getInt(String stringNumber)
	{
		try
		{
			return Integer.parseInt(stringNumber);
		}
		catch(Exception e){}
		return Integer.MIN_VALUE;
	}
	/**
	 * Creates a {@link List} of {@link Integer}s from an array of primitive {@code int}s.
	 * @param array the array of {@code int}s.
	 * @return the {@link List} of {@link Integer}s created from the array
	 */
	public static List<Integer> createListFromArray(int[] array)
	{
		List<Integer> list = new ArrayList<Integer>();
		for(int item : array)
			list.add(item);
		return list;
	}
}