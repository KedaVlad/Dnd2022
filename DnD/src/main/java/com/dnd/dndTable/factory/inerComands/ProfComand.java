package com.dnd.dndTable.factory.inerComands;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("PROF_COMAND")
public class ProfComand extends InerComand {

	private long key;
	private String target;
	private boolean competence;
	
	public static ProfComand create(long key, String target)
	{
		ProfComand answer = new ProfComand();
		answer.key = key;
		answer.target = target;
		return answer;
	}
	
	public ProfComand competence()
	{
		this.competence = true;
		return this;
	}
	
	private static final long serialVersionUID = 1L;

	public long getKey() {
		return key;
	}

	public String getTarget() {
		return target;
	}

	public boolean isCompetence() {
		return competence;
	}

}
