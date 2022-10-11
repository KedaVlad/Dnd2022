package com.dnd.dndTable.gameEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.creatingDndObject.skills.Features;
import com.dnd.dndTable.creatingDndObject.skills.Possession;
import com.dnd.dndTable.creatingDndObject.skills.Spell;
import com.dnd.dndTable.creatingDndObject.skills.Workmanship;

public class MyWorkmanship implements Serializable {

	
	private static final long serialVersionUID = -4381963215622900771L;

	private List<Workmanship[]> pendingWorkmanship = new ArrayList<>();

	private List<Features> myFeatures = new ArrayList<>();
	private List<Spell> mySpells = new ArrayList<>();
	private List<Possession> myPossession = new ArrayList<>();;

	public List<Workmanship[]> getPendingWorkmanship() 
	{
		return pendingWorkmanship;
	}

	public void setPendingWorkmanship(List<Workmanship[]> pendingWorkmanship) 
	{
		this.pendingWorkmanship = pendingWorkmanship;
	}

	public List<Features> getMyFeatures() 
	{
		return myFeatures;
	}

	public void setMySkills(List<Features> mySkills) 
	{
		this.myFeatures = mySkills;
	}

	public List<Spell> getMySpells() 
	{
		return mySpells;
	}

	public void setMySpells(List<Spell> mySpells) 
	{
		this.mySpells = mySpells;
	}

	public List<Possession> getMyPossession() 
	{
		return myPossession;
	}

	public void setMyPossession(List<Possession> myPossession) 
	{
		this.myPossession = myPossession;
	}

}
