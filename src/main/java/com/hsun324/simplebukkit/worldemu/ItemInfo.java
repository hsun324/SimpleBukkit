package com.hsun324.simplebukkit.worldemu;

import org.bukkit.Material;

public class ItemInfo
{
	private final Material item;
	private final int amount;
	private final byte data;
	
	public ItemInfo(Material item, int amount)
	{
		this.item = item;
		this.amount = amount;
		this.data = 0;
	}
	
	public ItemInfo(Material item, int amount, byte data)
	{
		this.item = item;
		this.amount = amount;
		this.data = data;
	}
	
	public Material getItem()
	{
		return item;
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public byte getData()
	{
		return data;
	}
}
