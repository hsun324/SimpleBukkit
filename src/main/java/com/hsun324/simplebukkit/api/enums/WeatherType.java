package com.hsun324.simplebukkit.api.enums;

public enum WeatherType
{
	CLEAR(false, false),
	RAINY(true, false),
	THUNDERING(true, true);
	
	private final boolean isRainy;
	private final boolean hasLighting;
	
	private WeatherType(boolean isRainy, boolean hasLighting)
	{
		this.isRainy = isRainy;
		this.hasLighting = hasLighting;
	}
	
	public boolean isRainy()
	{
		return this.isRainy;
	}
	public boolean hasLighting()
	{
		return this.hasLighting;
	}
}
