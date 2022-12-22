package com.dnd.dndTable.factory.inerComands;

import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.creatingDndObject.workmanship.mechanics.Pool;
import com.dnd.dndTable.creatingDndObject.workmanship.mechanics.SoftPool;
import com.dnd.dndTable.creatingDndObject.workmanship.mechanics.TimePool;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("CLOUD_COMAND")
@JsonSubTypes({@JsonSubTypes.Type(value = IntegrateCloud.class, name = "INEGRATE_CLOUD")})
public class CloudComand extends InerComand{

	private static final long serialVersionUID = 1L;
	protected Pool<InerComand> pool;
	protected String text;

	public static CloudComand create(int times, String text, boolean soft, InerComand... comands)
	{
		CloudComand answer = new CloudComand();
		List<InerComand> pool = new ArrayList<>();
		for(InerComand comand: comands)
		{
			pool.add(comand);
		}

		if(soft)
		{
			answer.pool = new SoftPool<InerComand>().pool(pool).times(times);
		}
		else
		{
			answer.pool = new TimePool<InerComand>().pool(pool).times(times);
		}
		answer.text = text;
		return answer;
	}

	public Pool<InerComand> getPool() 
	{
		return pool;
	}

	public String getText() {
		return text;
	}

	public boolean isSoft() 
	{
		if(pool.getClass().equals(SoftPool.class))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static CloudComand lvlUp(InerComand comands)
	{
		if(comands != null)
		{
			return CloudComand.create(1,
					"U shood to choose STATS or TRAIT or your spesials oportunities",
					false,
					IntegrateCloud.create(2, "STAT", true, com.dnd.dndTable.DndKeyWallet.stat),
					IntegrateCloud.create(1, "TRAIT", false, com.dnd.dndTable.DndKeyWallet.trait), 
					comands);
			
		}
		else
		{
			return CloudComand.create(1,
					"U shood to choose STATS or TRAIT",
					false,
					IntegrateCloud.create(2, "STAT", true, com.dnd.dndTable.DndKeyWallet.stat),
					IntegrateCloud.create(1, "TRAIT", false, com.dnd.dndTable.DndKeyWallet.trait));
		}
	}
}
