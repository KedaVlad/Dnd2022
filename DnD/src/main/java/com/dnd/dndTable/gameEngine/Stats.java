package com.dnd.dndTable.gameEngine;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Stats implements Serializable {
	
	private static final long serialVersionUID = 7901749239721687760L;

	private Map<String, Integer> skills = new LinkedHashMap<>();

	private Map<StatName, Integer> stats = new LinkedHashMap<>();
	
	private Map<StatName[], Integer> FreeStat = new LinkedHashMap<>();
	
	public enum StatName
	{
		STRENGTH, DEXTERITY, CONSTITUTION, INTELLIGENSE, WISDOM, CHARISMA, FREE
	}

	public void updateSkills()
	{
		skills.put("Acrobatics", stats.get(StatName.DEXTERITY));
	}
	
	
	
	public Map<StatName, Integer> getStats() 
	{
		return stats;
	}

	public void setStartStat(int str, int dex, int con, int intl, int wis, int cha)
	{
		setStats(StatName.STRENGTH, str);
		setStats(StatName.DEXTERITY, dex);
		setStats(StatName.CONSTITUTION, con);
		setStats(StatName.INTELLIGENSE, intl);
		setStats(StatName.WISDOM, wis);
		setStats(StatName.CHARISMA, cha);
		
	}
	
	public void setStats(StatName name, Integer number) 
	{
	
		if(stats.containsKey(name)) 
		{
			stats.put(name, stats.get(name) + number);
		}
		else
		{
			stats.put(name, number);
		}
	}
	
	public Map<StatName[], Integer> getFreeStat() {
		return FreeStat;
	}

	@SuppressWarnings("unlikely-arg-type")
	public void setFreeStat(Integer number, StatName... name) {
		
		if(stats.containsKey(name)) 
		{
			FreeStat.put(name, FreeStat.get(name) + number);
		}
		else
		{
			FreeStat.put(name, number);
		}
	}



	
	public Map<String, Integer> getSkills() {
		return skills;
	}
	
	
	
}
