package com.dnd.dndTable.creatingDndObject.modification.pool;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("TIME_POOL")
public class TimePool<T> extends Pool<T> 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int times;

	public TimePool<T> pool(List<T> active)
	{
		this.active = active;
		return this;
	}
	
	public TimePool<T> times(int times)
	{
		this.times = times;
		return this;
	}
	
	public T use(int target)
	{
		times--;
		T answer = this.active.get(target);
		this.active.remove(target);
		return answer;
	}

	public int getTimes() {
		return times;
	}
	
	public boolean active()
	{
		return times > 0;
	}


}
