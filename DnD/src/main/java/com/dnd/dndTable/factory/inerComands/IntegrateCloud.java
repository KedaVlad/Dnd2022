package com.dnd.dndTable.factory.inerComands;

import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.creatingDndObject.modification.pool.SoftPool;
import com.dnd.dndTable.creatingDndObject.modification.pool.TimePool;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeName;


@JsonTypeName("INEGRATE_CLOUD")
public class IntegrateCloud extends CloudComand {

	private static final long serialVersionUID = 1L;
	private long key;
	
	
	
	public static IntegrateCloud create(int times, String text, boolean soft, long key, InerComand... comands)
	{
		IntegrateCloud answer = new IntegrateCloud();
		answer.key = key;
		List<InerComand> pool = new ArrayList<>();
		for(InerComand comand: comands)
		{
			pool.add(comand);
		}
		
		if(soft)
		{
		//	answer.pool = new SoftPool<InerComand>().pool(pool).times(times);
		}
		else
		{
		//	answer.pool = new TimePool<InerComand>().pool(pool).times(times);
		}
		answer.text = text;
		return answer;
	}

	public long getKey() 
	{
		return key;
	}

}
