package com.hsun324.simplebukkit.config;

import java.util.List;

import com.hsun324.simplebukkit.config.ConfigurationWrapper;
import com.hsun324.simplebukkit.config.NodeBrowser;

public class NodeBrowser
{
	protected final ConfigurationWrapper config;
	protected final String location;
	
	private NodeBrowser(ConfigurationWrapper config, String location)
	{
		this.config = config;
		this.location = location;
	}
	
	public static NodeBrowser create(ConfigurationWrapper config, String location)
	{
		if(config == null || location == null)
			throw new NullPointerException();
		return new NodeBrowser(config, location);
	}
	
	public ConfigurationWrapper getRoot()
	{
		return config;
	}
	
	public NodeBrowser getRootNode()
	{
		return new NodeBrowser(config, "");
	}
	public NodeBrowser getParentNode()
	{
		return new NodeBrowser(config, getParentNodeLocation());
	}
	public NodeBrowser getChildNode(String name)
	{
		return new NodeBrowser(config, location + "." + name);
	}
	public NodeBrowser[] getChildNodes()
	{
		List<String> children = config.getKeys(location);
		NodeBrowser[] list = new NodeBrowser[children.size()];
		int i = 0;
		for(String child : children)
			list[i++] = new NodeBrowser(config, location + "." + child);
		return list;
	}
	
	public boolean exists()
	{
		Object thisProperty = config.getHandle().getProperty(location);
		return thisProperty != null;
	}

	public String getNodeLocation()
	{
		return location;
	}
	public String getParentNodeLocation()
	{
		if(location.contains("."))
		{
			int lastIndex = location.lastIndexOf(".");
			if(lastIndex > 0)
				return location.substring(0, lastIndex);
		}
		return "";
	}

	public Object getValue()
	{
		return config.getHandle().getProperty(location);
	}
	public Object setValue(Object value)
	{
		config.setProperty(location, value);
		return value;
	}
	
	public String getAsString(String defaultResult)
	{
		return config.getString(location, defaultResult);
	}
	public void setAsString(String value)
	{
		config.setString(location, value);
	}
	public int getAsInt(int defaultResult)
	{
		return config.getInt(location, defaultResult);
	}
	public void setAsInt(int value)
	{
		config.setInt(location, value);
	}
	public float getAsFloat(float defaultResult)
	{
		return config.getFloat(location, defaultResult);
	}
	public void setAsFloat(float value)
	{
		config.setFloat(location, value);
	}
	public double getAsDouble(double defaultResult)
	{
		return config.getDouble(location, defaultResult);
	}
	public void setAsDouble(double value)
	{
		config.setDouble(location, value);
	}
	public boolean getAsBoolean(boolean defaultResult)
	{
		return config.getBoolean(location, defaultResult);
	}
	public void setAsBoolean(boolean value)
	{
		config.setBoolean(location, value);
	}
	public List<String> getAsStringList(List<String> defaultResult)
	{
		return config.getStringList(location, defaultResult);
	}
	public List<String> getAsStringList(String[] defaultResult)
	{
		return config.getStringList(location, defaultResult);
	}
	public void setAsStringList(List<String> value)
	{
		config.setStringList(location, value);
	}
	public void setAsStringList(String[] value)
	{
		config.setStringList(location, value);
	}
	public List<Integer> getAsIntList(List<Integer> defaultResult)
	{
		return config.getIntList(location, defaultResult);
	}
	public List<Integer> getAsIntList(int[] defaultResult)
	{
		return config.getIntList(location, defaultResult);
	}
	public void setAsIntList(List<Integer> value)
	{
		config.setIntList(location, value);
	}
	public void setAsIntList(int[] value)
	{
		config.setIntList(location, value);
	}

	public void load()
	{
		config.load();
	}
	public boolean save()
	{
		return config.save();
	}
}
