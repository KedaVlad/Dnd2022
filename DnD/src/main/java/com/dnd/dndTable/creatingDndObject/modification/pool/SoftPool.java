package com.dnd.dndTable.creatingDndObject.modification.pool;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("SOFT_POOL")
public class SoftPool<T> extends Pool<T> 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int times;
	
	public T get(int target)
	{
		times--;
		return this.active.get(target);
	}

	public SoftPool<T> times(int times)
	{
		this.times = times;
		return this;
	}
	
	public SoftPool<T> pool(List<T> active)
	{
		this.active = active;
		return this;
	}

	public int getTimes() 
	{
		return times;
	}
}
