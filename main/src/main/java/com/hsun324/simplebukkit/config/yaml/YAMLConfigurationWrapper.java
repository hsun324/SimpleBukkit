package com.hsun324.simplebukkit.config.yaml;

import java.io.File;
import java.util.List;

import org.bukkit.util.config.Configuration;

import com.hsun324.simplebukkit.config.ConfigurationWrapper;
import com.hsun324.simplebukkit.util.IntegerUtils;
import com.hsun324.simplebukkit.util.Util;

public class YAMLConfigurationWrapper implements ConfigurationWrapper
{
	private final YAMLConfiguration configReader;
	
	public YAMLConfigurationWrapper(String configFile)
	{
		this(new File(configFile));
	}
	
	public YAMLConfigurationWrapper(File configFile)
	{
		configReader = new YAMLConfiguration(configFile.getAbsoluteFile());
	}

	public String getString(String property, String defaultResult)
	{
		String result = configReader.getString(property, defaultResult);
		configReader.setProperty(property, result);
		return result;
	}
	public void setString(String property, String value)
	{
		configReader.setProperty(property, value);
	}

	public int getInt(String property, int defaultResult)
	{
		int result = configReader.getInt(property, defaultResult);
		configReader.setProperty(property, result);
		return result;
	}
	public void setInt(String property, int value)
	{
		configReader.setProperty(property, value);
	}

	public float getFloat(String property, float defaultResult)
	{
		float result = (float) configReader.getDouble(property, defaultResult);
		configReader.setProperty(property, result);
		return result;
	}
	public void setFloat(String property, float value)
	{
		configReader.setProperty(property, (double) value);
	}

	public double getDouble(String property, double defaultResult)
	{
		double result = configReader.getDouble(property, defaultResult);
		configReader.setProperty(property, result);
		return result;
	}
	public void setDouble(String property, double value)
	{
		configReader.setProperty(property, value);
	}

	public boolean getBoolean(String property, boolean defaultResult)
	{
		boolean result = configReader.getBoolean(property, defaultResult);
		configReader.setProperty(property, result);
		return result;
	}
	public void setBoolean(String property, boolean value)
	{
		configReader.setProperty(property, value);
	}

	public List<String> getKeys(String property)
	{
		return configReader.getKeys(property);
	}
	public List<String> getStringList(String property, List<String> defaultResult)
	{
		List<String> result = configReader.getStringList(property, defaultResult);
		configReader.setProperty(property, result);
		return result;
	}
	
	public List<String> getStringList(String property, String[] defaultResult)
	{
		List<String> result = configReader.getStringList(property, Util.createListFromArray(defaultResult));
		configReader.setProperty(property, result);
		return result;
	}
	public void setStringList(String property, List<String> value)
	{
		configReader.setProperty(property, value);
	}

	public void setStringList(String property, String[] value)
	{
		configReader.setProperty(property, Util.createListFromArray(value));
	}
	public List<Integer> getIntList(String property, List<Integer> defaultResult)
	{
		List<Integer> list = configReader.getIntList(property, defaultResult);
		setIntList(property, list);
		return list;
	}

	public List<Integer> getIntList(String property, int[] defaultResult)
	{
		List<Integer> list = configReader.getIntList(property, IntegerUtils.createListFromArray(defaultResult));
		setIntList(property, list);
		return list;
	}
	public void setIntList(String property, List<Integer> value)
	{
		configReader.setProperty(property, value);
	}

	public void setIntList(String property, int[] value)
	{
		configReader.setProperty(property, IntegerUtils.createListFromArray(value));
	}
	public void setProperty(String property, Object object)
	{
		configReader.setProperty(property, object);
	}

	public void load()
	{
		configReader.load();
	}
	public boolean save()
	{
		return configReader.save();
	}
	
	public Configuration getHandle()
	{
		return configReader;
	}
}
