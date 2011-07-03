package com.hsun324.simplebukkit.worldemu;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemDropInfo
{
	private final Material item;
	private final int chance;
	private final int fromAmount;
	private final int toAmount;
	private final byte data;
	
	public ItemDropInfo(Material item, int chance, int fromAmount, int toAmount)
	{
		this.item = item;
		if(chance > 100)
			chance = 100;
		if(chance < 0)
			chance = 0;
		this.chance = chance;
		this.fromAmount = fromAmount;
		if(fromAmount > toAmount)
			throw new RuntimeException("fromAmount must be smaller than or equal to toAmount");
		this.toAmount = toAmount;
		this.data = 0;
	}
	
	public ItemDropInfo(Material item, int chance, int fromAmount, int toAmount, byte data)
	{
		this.item = item;
		if(chance > 100)
			chance = 100;
		if(chance < 0)
			chance = 0;
		this.chance = chance;
		this.toAmount = toAmount;
		this.fromAmount = fromAmount;
		if(fromAmount > toAmount)
			throw new RuntimeException("fromAmount must be smaller than or equal to toAmount");
		this.data = data;
	}
	
	public Material getItem()
	{
		return item;
	}
	
	public int getChance()
	{
		return chance;
	}
	
	public int getFromAmount()
	{
		return fromAmount;
	}
	
	public int getToAmount()
	{
		return toAmount;
	}
	
	public byte getData()
	{
		return data;
	}
	
	public void tryDrop(Location location, Random random)
	{
		if(random.nextInt(100) < chance)
			drop(location, random);
	}
	public void drop(Location location, Random random)
	{
		int amount = fromAmount;
		ItemStack stack = new ItemStack(item, 1, data);
		if(toAmount - fromAmount > 0)
			amount = fromAmount + random.nextInt(toAmount - fromAmount);
		while(amount-- > 0)
			location.getWorld().dropItemNaturally(location, stack);
	}
}
