package com.hsun324.simplebukkit.config;

import java.util.List;

import org.bukkit.util.config.Configuration;

public interface ConfigurationWrapper
{
	public String getString(String property, String defaultResult);
	public void setString(String property, String value);
	
	public int getInt(String property, int defaultResult);
	public void setInt(String property, int value);
	
	public float getFloat(String property, float defaultResult);
	public void setFloat(String property, float value);
	
	public double getDouble(String property, double defaultResult);
	public void setDouble(String property, double value);
	
	public boolean getBoolean(String property, boolean defaultResult);
	public void setBoolean(String property, boolean value);

	public List<String> getKeys(String property);
	
	public List<String> getStringList(String property, List<String> defaultResult);
	public List<String> getStringList(String property, String[] defaultResult);
	public void setStringList(String property, List<String> value);
	public void setStringList(String property, String[] value);

	public List<Integer> getIntList(String property, List<Integer> defaultResult);
	public List<Integer> getIntList(String property, int[] defaultResult);
	public void setIntList(String property, List<Integer> value);
	public void setIntList(String property, int[] value);
	
	public void setProperty(String property, Object object);
	
	public Configuration getHandle();

	public void load();
	public boolean save();
}
