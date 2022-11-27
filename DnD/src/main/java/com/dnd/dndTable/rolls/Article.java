package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Names.Stat;

public class Article implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name;
	private Stat depends;
	private boolean proficiency;
	private boolean halfProf;
	private boolean competense;
	List <String> spesial;
	List <Dice> permanentBuff;

	Article(String name, Stat depends)
	{
		this.setName(name);
		this.setDepends(depends);
		spesial = new ArrayList<>();
		permanentBuff = new ArrayList<>();
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
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Stat getDepends() {
		return depends;
	}

	public void setDepends(Stat depends) {
		this.depends = depends;
	}

	public boolean isProficiency() {
		return proficiency;
	}

	public void setProficiency(boolean proficiency) {
		this.proficiency = proficiency;
	}

	public boolean isHalfProf() {
		return halfProf;
	}

	public void setHalfProf(boolean halfProf) {
		this.halfProf = halfProf;
	}

	public boolean isCompetense() {
		return competense;
	}

	public void setCompetense(boolean competense) {
		this.competense = competense;
	}
	
}