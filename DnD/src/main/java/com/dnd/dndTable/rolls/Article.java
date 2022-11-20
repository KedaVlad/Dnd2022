package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Dice;
import com.dnd.Names.Stat;

public class Article implements Serializable
{
	private static final long serialVersionUID = 1L;
	String name;
	Stat depends;
	boolean proficiency;
	boolean halfProf;
	boolean competense;
	List <String> spesial;
	List <Dice> permanentBuff;

	Article(String name, Stat depends)
	{
		this.name = name;
		this.depends = depends;
		spesial = new ArrayList<>();
		permanentBuff = new ArrayList<>();
	}

	public void setProficiency(boolean proficiency) {
		this.proficiency = proficiency;
	}

	public void setHalfProf(boolean halfProf) {
		this.halfProf = halfProf;
	}

	public void setCompetense(boolean competense) {
		this.competense = competense;
	}
	
}