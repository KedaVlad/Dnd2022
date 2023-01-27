package com.dnd.dndTable.creatingDndObject.workmanship;

import java.io.IOException;
import java.io.Serializable;
import com.dnd.Names.SpellClass;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.dndTable.creatingDndObject.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.workmanship.casts.Cast;
import com.dnd.dndTable.creatingDndObject.workmanship.casts.Summon;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("SPELL")
public class Spell implements Serializable, ObjectDnd 
{
	private static final long serialVersionUID = -7876613939972469105L;
	
	private int lvlSpell;
	private Cast cast;
	private String name;
	private String description;
	private String applicationTime;
	private String distanse;
	private String duration;
	private SpellClass[] classFor;
	private boolean concentration;
	
	public Spell conentration()
	{
		this.concentration = true;
		return this;
	}
	
	public Act cast()
	{
		return Caster.cast(cast);
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getApplicationTime() {
		return applicationTime;
	}

	public String getDistanse() {
		return distanse;
	}

	public String getDuration() {
		return duration;
	}

	public String toString() {
		return getName() + ": application time("+getApplicationTime()+"), distance("+getDistanse()+"), duration ("+getDuration()+").";
	}

	public int getLvlSpell() {
		return lvlSpell;
	}

	public SpellClass[] getClassFor() {
		return classFor;
	}
	
	public static Spell create(int lvlSpell, Cast cast, String name, String description, String applicationTime, String distanse, String duration, SpellClass...classes) 
	{
		Spell answer = new Spell();
		answer.lvlSpell = lvlSpell;
		answer.cast = cast;
		answer.name = name;
		answer.description = description;
		answer.applicationTime = applicationTime;
		answer.distanse = distanse;
		answer.duration = duration;
		answer.classFor = classes;
		return answer;
		
	}

	public Cast getCast() 
	{
		return cast;
	}

	public void setCast(Cast cast) 
	{
		this.cast = cast;
	}

	public static void main(String[] args) throws IOException 
	{
		Spell[] pool = new Spell[]
				{
						Spell.create(
								0, 
								Summon.create("Test sommon some info which need to reed for some time... adter no longer nee eliminate"), 
								"Simple Summon" ,
								"Some spell which sommon some simple effect", 
								"reaction", 
								"30 ft.",
								"10 min.", 
								SpellClass.ARTIFACER, SpellClass.SORCER, SpellClass.WIZARD),
						Spell.create(
								1, 
								Summon.create("Test sommon some info which need to reed for some time... adter no longer nee eliminate"), 
								"Simple Summon" ,
								"Some spell which sommon some simple effect", 
								"reaction", 
								"30 ft.",
								"10 min.", 
								SpellClass.ARTIFACER, SpellClass.WIZARD),
				};
		
	}

	/**
	 * @return the concentration
	 */
	public boolean isConcentration() {
		return concentration;
	}



}

