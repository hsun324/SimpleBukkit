package com.hsun324.simplebukkit.util;

public final class ByteUtil
{
	/**
	 * Parses the given String into a byte.
	 * @param stringNumber the number to parse
	 * @return the parsed string if successful. {@link Byte#MIN_VALUE} if not.
	 */
	public static byte getByte(String stringNumber)
	{
		try
		{
			return Byte.parseByte(stringNumber);
		}
		catch(Exception e){}
		return Byte.MIN_VALUE;
	}
}