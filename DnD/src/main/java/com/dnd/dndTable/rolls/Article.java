package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.Rolls.Proficiency;

public class Article implements Serializable, ObjectDnd
{
	private static final long serialVersionUID = 1L;
	private String name;
	private Stat depends;
	private Proficiency proficiency;
	private List <String> spesial;

	public Article(String name, Stat depends)
	{
		this.setName(name);
		this.setDepends(depends);
		spesial = new ArrayList<>();
	}
	
	public String getSpesial()
	{
		String answer = "";
		for(String string: spesial)
		{
			answer += string + "\n";
		}
		return answer;
	}
	
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public Stat getDepends() 
	{
		return depends;
	}

	public void setDepends(Stat depends) 
	{
		this.depends = depends;
	}

	public boolean isProficiency() 
	{
		return proficiency != null;
	}

	public void addSpesial(String spesial) 
	{
		this.spesial.add(spesial);
	}

	
	@Override
	public long key() {
		// TODO Auto-generated method stub
		return ARTICLE;
	}

	public Proficiency getProficiency() 
	{
		return proficiency;
	}

	public void setProficiency(Proficiency proficiency) 
	{
		this.proficiency = proficiency;
	}
	
}